package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class SingUpPresenter extends FragmentPresenter{
    private final EditText newNameEditText;
    private final EditText newSurnameEditText;
    private final EditText newUsernameEditText;
    private final EditText newEmailEditText;

    public SingUpPresenter(SingUpFragment singUpFragment, @NonNull View inflate){
        super(singUpFragment);


        newNameEditText = inflate.findViewById(R.id.newNameEditText);
        newSurnameEditText = inflate.findViewById(R.id.newSurnameEditText);
        newUsernameEditText = inflate.findViewById(R.id.newUsernameEditText);
        newEmailEditText = inflate.findViewById(R.id.newEmailEditText);
    }

    public void pressCancelButton(){ getFragmentManager().popBackStack(); }

    private boolean containsWhiteSpace(String str){
        if(str == null)
            return true;

        for (int i = 0; i < str.length(); i++){
            if (Character.isWhitespace(str.charAt(i))){
                return true;
            }
        }

        return false;
    }

    public static boolean checkEmailCorrectFormat(String eMail){
        boolean ret;

        if(eMail == null){
            ret = false;
        }else{
            Pattern validEmailAddressRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            Matcher matcher = validEmailAddressRegex.matcher(eMail);
            ret = matcher.find();
        }

        return ret;
    };

    public void pressNextButton(){
        //required fields check
        if( isEmptyEditText(newNameEditText) || isEmptyEditText(newSurnameEditText) ||
            isEmptyEditText(newEmailEditText)  || isEmptyEditText(newUsernameEditText)
        ){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        if(checkEmailCorrectFormat(String.valueOf(newEmailEditText))){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_email_format));
            return;
        }

        if(containsWhiteSpace(String.valueOf(newNameEditText.getText())) || containsWhiteSpace(String.valueOf(newUsernameEditText.getText()))){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.whithespace_error));
            return;
        }

        goToInsertPassword();
    }

    private void goToInsertPassword(){
        String name = String.valueOf(newNameEditText.getText()).toUpperCase();
        String surname = String.valueOf(newSurnameEditText.getText()).toUpperCase();
        String nickname = String.valueOf(newUsernameEditText.getText());
        String eMail = String.valueOf(newEmailEditText.getText()).toLowerCase();

        SingUpPasswordFragment singUpPasswordFragment = new SingUpPasswordFragment(name, surname, eMail, nickname);

        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, singUpPasswordFragment).commit();
    }

    public void pressAlreadyCodeButton(){
        if(isEmptyEditText(newEmailEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.email_already_code));
            return;
        }

        String eMail = String.valueOf(newEmailEditText.getText());
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
    }
}
