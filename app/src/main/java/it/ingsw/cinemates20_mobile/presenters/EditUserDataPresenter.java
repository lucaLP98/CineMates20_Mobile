package it.ingsw.cinemates20_mobile.presenters;

import android.widget.EditText;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.views.fragments.EditPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.EditUserDataFragment;
import it.ingsw.cinemates20_mobile.views.fragments.FilmFragment;

public class EditUserDataPresenter extends FragmentPresenter{
    private final EditUserDataFragment editUserDataFragment;

    public EditUserDataPresenter(EditUserDataFragment editUserDataFragment){
        super(editUserDataFragment);

        this.editUserDataFragment = editUserDataFragment;

        setEditTextValue();

        editUserDataFragment.getCancellEditutton().setOnClickListener( v -> pressCancellButton());
        editUserDataFragment.getEditPasswordButton().findViewById(R.id.editPasswordButton).setOnClickListener( v -> pressEditPassword());
        editUserDataFragment.getSaveChangesUserDataButton().findViewById(R.id.saveChangesUserDataButton).setOnClickListener( v -> pressSaveChanges());
    }

    private void setEditTextValue(){
        if(ThisUser.getInstance() != null){
            editUserDataFragment.setEditNameEditText(ThisUser.getInstance().getName());
            editUserDataFragment.setEditBioEditText(ThisUser.getInstance().getBiography());
            editUserDataFragment.setEditNicknameEditText(ThisUser.getInstance().getNickname());
            editUserDataFragment.setEditSurnameameEditText(ThisUser.getInstance().getSurname());
        }
    }

    private boolean containsWhiteSpace(@NonNull String str){
        for (int i = 0; i < str.length(); i++){
            if (Character.isWhitespace(str.charAt(i))){
                return true;
            }
        }

        return false;
    }

    private void pressSaveChanges(){
        EditText editName = editUserDataFragment.getEditNameEditText();
        EditText editSruname = editUserDataFragment.getEditSrunameEditText();
        EditText editNickname = editUserDataFragment.getEditNicknameEditText();
        EditText editBio = editUserDataFragment.getEditBioEditText();

        if(isEmptyEditText(editName) || isEmptyEditText(editSruname) || isEmptyEditText(editNickname)){
            showErrorMessage(getContext().getResources().getString(R.string.edit_data_error_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        String name = String.valueOf(editName.getText()).toUpperCase();
        String surname = String.valueOf(editSruname.getText()).toUpperCase();
        String nickname = String.valueOf(editNickname.getText());
        String bio = (isEmptyEditText(editBio)) ? "" : String.valueOf(editBio.getText());

        if(containsWhiteSpace(name) || containsWhiteSpace(nickname)){
            showErrorMessage(getContext().getResources().getString(R.string.edit_data_error_label), getContext().getResources().getString(R.string.whithespace_error));
            return;
        }

        DAOFactory.getUserDao().editUserData(name, surname, nickname, bio, getContext());
        showSuccessMessage(getContext().getResources().getString(R.string.edit_data_success_label), getContext().getResources().getString(R.string.edit_data_success_msg));
        getFragmentManager().beginTransaction().replace(R.id.home_page_container, new FilmFragment(), FilmFragment.filmFragmentLabel).commit();
    }

    private void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    private void pressEditPassword(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new EditPasswordFragment()).commit();
    }
}
