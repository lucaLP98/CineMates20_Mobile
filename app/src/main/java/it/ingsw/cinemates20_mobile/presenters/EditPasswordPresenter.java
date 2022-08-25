package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.EditPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;

public class EditPasswordPresenter extends FragmentPresenter{
    private final EditPasswordFragment editPasswordFragment;

    public EditPasswordPresenter(EditPasswordFragment editPasswordFragment){
        super(editPasswordFragment);

        this.editPasswordFragment = editPasswordFragment;

        editPasswordFragment.getCancelEditPasswordButton().setOnClickListener( v -> pressCancellButton());
        editPasswordFragment.getSaveChangePasswordButton().setOnClickListener( v -> pressSaveChangesPassword());
    }

    private void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    private void pressSaveChangesPassword(){
        if(isEmptyEditText(editPasswordFragment.getOldPasswordEditText()) ||
                isEmptyEditText(editPasswordFragment.getNewPasswordEditText()) ||
                isEmptyEditText(editPasswordFragment.getRepeatNewPasswordEditText())){
            showErrorMessage(getContext().getResources().getString(R.string.edit_password_error), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        String newPsw = String.valueOf(editPasswordFragment.getNewPasswordEditText().getText());
        String repeatNewPsw = String.valueOf(editPasswordFragment.getRepeatNewPasswordEditText().getText());
        String oldPsw = String.valueOf(editPasswordFragment.getOldPasswordEditText().getText());

        if(!matchPassword(newPsw, repeatNewPsw)){
            showErrorMessage(getContext().getResources().getString(R.string.edit_password_error), getContext().getResources().getString(R.string.error_not_matched_password));
            return;
        }

        changePassword(oldPsw, newPsw);
    }

    private void changePassword(String oldPsw, String newPsw){
        CognitoSettings cognito = CognitoSettings.getInstance(getContext());

        CognitoUser user = cognito.getUserPool().getUser(ThisUser.getInstance().getEmail());
        user.changePasswordInBackground(oldPsw, newPsw, new GenericHandler() {
            @Override
            public void onSuccess() {
                showSuccessMessage(getContext().getResources().getString(R.string.edit_password_success_label), getContext().getResources().getString(R.string.edit_password_success_msg));
                getFragmentManager().beginTransaction().replace(R.id.home_page_container, new ProfileFragment(), ProfileFragment.profileFragmentLabel).commit();
            }

            @Override
            public void onFailure(Exception exception) {
               showErrorMessage(getContext().getResources().getString(R.string.edit_password_error), exception.getLocalizedMessage());
            }
        });
    }

    private boolean matchPassword(@NonNull String psw1, @NonNull String psw2){
        return (psw1.equals(psw2));
    }
}
