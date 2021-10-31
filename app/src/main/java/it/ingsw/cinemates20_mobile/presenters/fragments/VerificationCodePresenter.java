package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class VerificationCodePresenter extends FragmentPresenter {
    private final String eMail;

    private final EditText codeEditText;

    public VerificationCodePresenter(VerificationCodeSingUpFragment verificationCodeFragment, View inflate, String eMail){
        super(verificationCodeFragment);

        codeEditText = inflate.findViewById(R.id.verificationCodeEditText);

        this.eMail = eMail;
    }

    public void clickConfirmRegistrazion(){

        if(isEmptyEditText(codeEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_verification_code_null));
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

            CognitoSettings cognitoSettings = new CognitoSettings(getContext());
            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strings[1]);
            thisUser.confirmSignUp(strings[0], false, confirmationCallback);

            return result[0];
        }

        @Override
        protected void onPostExecute (String result){
            if(result.equals(SUCCEDED)){

                /* INSERIMENTO NUOVO UTENTE IN DB */

                codeVerificationSuccess();
            }else{
                showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_verification_code));
            }
        }
    }

    private void codeVerificationSuccess(){
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.success_singup_label)
                .setMessage(R.string.success_singup_msg)
                .setPositiveButton("OK", (dialog, which) -> getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE))
                .show();
    }
}
