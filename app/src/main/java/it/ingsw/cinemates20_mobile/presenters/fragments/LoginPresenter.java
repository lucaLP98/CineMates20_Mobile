package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;

public class LoginPresenter {
    private LoginFragment loginFragment;

    public LoginPresenter(LoginFragment loginFragment){
        this.loginFragment = loginFragment;
    }

    public void pressCancelButton(){
        loginFragment.getActivity().getSupportFragmentManager().popBackStack();
    }

    public void pressLoginButton(){
        EditText emailEditText = loginFragment.getInflate().findViewById(R.id.eMailEditText);
        EditText pswEditText = loginFragment.getInflate().findViewById(R.id.passwordEditText);

        //required fields check
        if(isEmptyEditText(emailEditText) || isEmptyEditText(pswEditText)){
            Toast.makeText(loginFragment.getActivity(), R.string.error_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private boolean isEmptyEditText(@NonNull EditText editText){
        boolean isEmpty;

        if(editText.getText().toString().equals(""))    isEmpty = true;
        else    isEmpty = false;

        return isEmpty;
    }

}
