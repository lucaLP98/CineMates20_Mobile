package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.views.fragments.EditPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.EditUserDataFragment;
import it.ingsw.cinemates20_mobile.views.fragments.FilmFragment;

public class EditUserDataPresenter extends FragmentPresenter{

    private final EditText editName;
    private final EditText editSruname;
    private final EditText editNickname;
    private final EditText editBio;

    public EditUserDataPresenter(EditUserDataFragment editUserDataFragment, @NonNull View inflate){
        super(editUserDataFragment);

        editName = inflate.findViewById(R.id.editNameEditText);
        editSruname = inflate.findViewById(R.id.editSurnameEditText);
        editNickname = inflate.findViewById(R.id.editNicknameEditText);
        editBio = inflate.findViewById(R.id.editBioEditText);
    }

    public void setEditTextValue(){
        if(ThisUser.getInstance() != null){
            editName.setText(ThisUser.getInstance().getName());
            editSruname.setText(ThisUser.getInstance().getSurname());
            editNickname.setText(ThisUser.getInstance().getNickname());
            editBio.setText(ThisUser.getInstance().getBiography());
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

    public void pressSaveChanges(){
        if(!checkRequiredField()){
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

    private boolean checkRequiredField(){
        return !isEmptyEditText(editName) && !isEmptyEditText(editSruname) && !isEmptyEditText(editNickname);
    }

    public void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    public void pressEditPassword(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new EditPasswordFragment()).commit();
    }
}
