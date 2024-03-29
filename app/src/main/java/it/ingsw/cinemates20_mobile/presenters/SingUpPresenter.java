package it.ingsw.cinemates20_mobile.presenters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class SingUpPresenter extends FragmentPresenter{
    private final SingUpFragment singUpFragment;

    public SingUpPresenter(SingUpFragment singUpFragment){
        super(singUpFragment);

        this.singUpFragment = singUpFragment;

        singUpFragment.getNextButton().setOnClickListener( v -> pressNextButton());
        singUpFragment.getCancelSingUpButton().setOnClickListener( v -> pressCancelButton());
        singUpFragment.getAlreadyCodeButton().setOnClickListener( v -> pressAlreadyCodeButton());
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
    }

    private void pressNextButton(){
        //required fields check
        if( isEmptyEditText(singUpFragment.getNewNameEditText()) || isEmptyEditText(singUpFragment.getNewSurnameEditText()) ||
            isEmptyEditText(singUpFragment.getNewEmailEditText())  || isEmptyEditText(singUpFragment.getNewUsernameEditText())
        ){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        if(checkEmailCorrectFormat(String.valueOf(singUpFragment.getNewEmailEditText()))){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_email_format));
            return;
        }

        if(containsWhiteSpace(String.valueOf(singUpFragment.getNewNameEditText().getText())) || containsWhiteSpace(String.valueOf(singUpFragment.getNewEmailEditText().getText()))){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.whithespace_error));
            return;
        }

        goToInsertPassword();
    }

    private void goToInsertPassword(){
        String name = String.valueOf(singUpFragment.getNewNameEditText().getText()).toUpperCase();
        String surname = String.valueOf(singUpFragment.getNewSurnameEditText().getText()).toUpperCase();
        String nickname = String.valueOf(singUpFragment.getNewUsernameEditText().getText());
        String eMail = String.valueOf(singUpFragment.getNewEmailEditText().getText()).toLowerCase();

        SingUpPasswordFragment singUpPasswordFragment = new SingUpPasswordFragment(name, surname, eMail, nickname);

        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, singUpPasswordFragment).commit();
    }

    private void pressAlreadyCodeButton(){
        if(isEmptyEditText(singUpFragment.getNewEmailEditText())){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.email_already_code));
            return;
        }

        String eMail = String.valueOf(singUpFragment.getNewEmailEditText().getText());
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
    }
}
