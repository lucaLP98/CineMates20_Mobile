package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;

public class SingUpPresenter {
    private SingUpFragment singUpFragment;

    public SingUpPresenter(SingUpFragment singUpFragment){
        this.singUpFragment = singUpFragment;
    }

    public void pressCancelButton(){
        singUpFragment.getActivity().getSupportFragmentManager().popBackStack();
    }

    public void pressSingUpButton(){
        EditText newNameEditText = singUpFragment.getInflate().findViewById(R.id.newNameEditText);
        EditText newSurnameEditText = singUpFragment.getInflate().findViewById(R.id.newSurnameEditText);
        EditText newUsernameEditText = singUpFragment.getInflate().findViewById(R.id.newUsernameEditText);
        EditText newEmailEditText = singUpFragment.getInflate().findViewById(R.id.newEmailEditText);
        EditText newPasswordEditText = singUpFragment.getInflate().findViewById(R.id.newPasswordEditText);
        EditText repeatNameEditText = singUpFragment.getInflate().findViewById(R.id.repeatNewPasswordEditText);
        EditText newBirthDateEditText = singUpFragment.getInflate().findViewById(R.id.newBirthDateEditText);

        //required fields check
        if(
                isEmptyEditText(newNameEditText) || isEmptyEditText(newSurnameEditText) ||
                isEmptyEditText(newEmailEditText) || isEmptyEditText(newPasswordEditText) ||
                isEmptyEditText(newUsernameEditText) || isEmptyEditText(newBirthDateEditText) ||
                isEmptyEditText(repeatNameEditText)
        ){
            Toast.makeText(singUpFragment.getActivity(), R.string.error_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        //matching password check
        if(!matchingPassword(newPasswordEditText.getText().toString(), repeatNameEditText.getText() .toString())){
            Toast.makeText(singUpFragment.getActivity(), R.string.error_not_matched_password, Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private boolean isEmptyEditText(@NonNull EditText editText){
        boolean isEmpty;

        if(editText.getText().toString().equals(""))    isEmpty = true;
        else    isEmpty = false;

        return isEmpty;
    }

    private boolean matchingPassword(@NonNull String psw1, String psw2){
        boolean matched;

        if(psw1.equals(psw2))   matched = true;
        else    matched = false;

        return matched;
    }
}
