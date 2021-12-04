package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.EditPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;

public class EditPasswordPresenter extends FragmentPresenter{

    private final EditText oldPasswordEditText;
    private final EditText newPasswordEditText;
    private final EditText repeatNewPasswordEditText;

    public EditPasswordPresenter(EditPasswordFragment editPasswordFragment, @NonNull View inflate){
        super(editPasswordFragment);

        oldPasswordEditText = inflate.findViewById(R.id.oldPasswordChangePasswordEditText);
        newPasswordEditText = inflate.findViewById(R.id.newPasswordChangePasswordEditText);
        repeatNewPasswordEditText = inflate.findViewById(R.id.repeatNewPasswordChangePasswordEditText2);
    }

    public void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    public void pressSaveChangesPassword(){
        if(isEmptyEditText(oldPasswordEditText) || isEmptyEditText(newPasswordEditText) || isEmptyEditText(repeatNewPasswordEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.edit_password_error), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        String newPsw = String.valueOf(newPasswordEditText.getText());
        String repeatNewPsw = String.valueOf(repeatNewPasswordEditText.getText());
        String oldPsw = String.valueOf(oldPasswordEditText.getText());

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
