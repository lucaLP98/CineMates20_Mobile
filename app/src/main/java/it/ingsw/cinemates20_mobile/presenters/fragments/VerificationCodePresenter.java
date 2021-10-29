package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.google.zxing.Result;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class VerificationCodePresenter {
    private VerificationCodeSingUpFragment verificationCodeFragment;
    private String eMail;
    private String verificationCode;

    public VerificationCodePresenter(VerificationCodeSingUpFragment verificationCodeFragment, String eMail){
        this.verificationCodeFragment = verificationCodeFragment;
        this.verificationCode = verificationCode;
        this.eMail = eMail;
    }

    public void clickConfirmRegistrazion(){
        EditText codeEditText = verificationCodeFragment.getInflate().findViewById(R.id.verificationCodeEditText);

        if(isEmptyEditText(codeEditText)){
            Toast.makeText(verificationCodeFragment.getActivity(), R.string.error_verification_code_null, Toast.LENGTH_SHORT).show();
            return;
        }

        String code = String.valueOf(codeEditText.getText());
        new ConfirmCodeTask().execute(code, eMail);
    }

    private boolean isEmptyEditText(@NonNull EditText editText){
        return editText.getText().toString().equals("");
    }

    private class ConfirmCodeTask extends AsyncTask<String, Void, String> {
        private final String SUCCEDED = "Succeded";
        private final String FAILED = "Failed";

        @Override
        protected String doInBackground(@NonNull String... strings){
            final String[] result = new String[1];

            final GenericHandler confirmationCallback = new GenericHandler() {
                @Override
                public void onSuccess() { result[0] = SUCCEDED; }

                @Override
                public void onFailure(Exception exception) {
                    result[0] = FAILED;
                }
            };

            CognitoSettings cognitoSettings = new CognitoSettings(verificationCodeFragment.getActivity());
            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strings[1]);
            thisUser.confirmSignUp(strings[0], false, confirmationCallback);

            return result[0];
        };

        @Override
        protected void onPostExecute (String result){
            if(result.equals(SUCCEDED)){

                /* INSERIMENTO NUOVO UTENTE IN DB */

                showSuccessMessage();
            }else{
                showErrorMessage();
            }
        }
    }

    private void showSuccessMessage(){
        new AlertDialog.Builder(this.verificationCodeFragment.getActivity())
                .setTitle(R.string.success_singup_label)
                .setMessage(R.string.success_singup_msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        verificationCodeFragment.getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                })
                .show();
    }

    private void showErrorMessage(){
        new AlertDialog.Builder(this.verificationCodeFragment.getActivity())
                .setTitle(R.string.error_singup_label)
                .setMessage(R.string.error_verification_code)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
