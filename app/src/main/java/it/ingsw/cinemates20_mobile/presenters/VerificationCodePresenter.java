package it.ingsw.cinemates20_mobile.presenters;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class VerificationCodePresenter extends FragmentPresenter {
    private final String eMail;
    private final VerificationCodeSingUpFragment verificationCodeFragment;

    private static final String SUCCEDED = "Succeded";
    private static final String FAILED = "Failed";

    public VerificationCodePresenter(VerificationCodeSingUpFragment verificationCodeFragment, String eMail){
        super(verificationCodeFragment);

        this.verificationCodeFragment = verificationCodeFragment;
        this.eMail = eMail;

        verificationCodeFragment.getSingupVerificationCodeButton().setOnClickListener( v -> pressConfirmRegistrazion());
        verificationCodeFragment.getResendVerificationCodeButton().setOnClickListener( v -> pressResendVerificationCodeButton());
    }

    public void pressConfirmRegistrazion(){

        if(isEmptyEditText(verificationCodeFragment.getCodeEditText())){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_verification_code_null));
            return;
        }

        String code = String.valueOf(verificationCodeFragment.getCodeEditText().getText());
        new ConfirmCodeTask(this).execute(code, eMail);
    }

    private static class ConfirmCodeTask extends AsyncTask<String, Void, String> {
        private final FragmentPresenter presenter;

        public ConfirmCodeTask(FragmentPresenter presenter){
            super();
            this.presenter = presenter;
        }

        @Override
        protected String doInBackground(@NonNull String... strings){
            final String[] result = new String[1];

            final GenericHandler confirmationCallback = new GenericHandler() {
                @Override
                public void onSuccess() {
                    result[0] = SUCCEDED;
                }

                @Override
                public void onFailure(Exception exception) {
                    result[0] = FAILED;
                }
            };

            CognitoSettings cognitoSettings = CognitoSettings.getInstance(presenter.getContext());
            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strings[1]);
            thisUser.confirmSignUp(strings[0], false, confirmationCallback);

            return result[0];
        }

        private void codeVerificationSuccess(){
            new AlertDialog.Builder(presenter.getContext())
                    .setTitle(R.string.success_singup_label)
                    .setIcon(R.drawable.ic_baseline_done_outline_24)
                    .setMessage(R.string.success_singup_msg)
                    .setPositiveButton("OK", (dialog, which) -> presenter.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE))
                    .show();
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                codeVerificationSuccess();
            }else{
                presenter.showErrorMessage(presenter.getContext().getResources().getString(R.string.error_singup_label), presenter.getContext().getResources().getString(R.string.error_verification_code));
            }
        }
    }



    public void pressResendVerificationCodeButton(){
        CognitoSettings cognitoSettings = CognitoSettings.getInstance(getContext());
        CognitoUser thisUser = cognitoSettings.getUserPool().getUser(eMail);

        new ResendConfirmationCodeAsyncTask(this).execute(thisUser);
    }

    private static class ResendConfirmationCodeAsyncTask extends AsyncTask<CognitoUser, Void, String>{
        private final FragmentPresenter presenter;

        public ResendConfirmationCodeAsyncTask(FragmentPresenter presenter){
            super();
            this.presenter = presenter;
        }

        @Override
        protected String doInBackground(@NonNull CognitoUser... cognitoUser){
            final String[] result = new String[1];

            VerificationHandler resendVerificationCodeHandler = new VerificationHandler() {
                @Override
                public void onSuccess(CognitoUserCodeDeliveryDetails verificationCodeDeliveryMedium) {
                    result[0] = SUCCEDED;
                }

                @Override
                public void onFailure(@NonNull Exception exception) {
                    result[0] = exception.getLocalizedMessage();
                }
            };
            cognitoUser[0].resendConfirmationCode(resendVerificationCodeHandler);

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                presenter.showSuccessMessage(presenter.getContext().getResources().getString(R.string.resend_verification_code_success_label), presenter.getContext().getResources().getString(R.string.resend_verification_code_success_msg));
            }else{
                presenter.showErrorMessage(presenter.getContext().getResources().getString(R.string.resend_verification_code_error_label), result);
            }
        }
    }
}
