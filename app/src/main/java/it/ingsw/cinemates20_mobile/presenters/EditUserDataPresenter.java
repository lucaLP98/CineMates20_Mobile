package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.views.fragments.EditUserDataFragment;
import it.ingsw.cinemates20_mobile.views.fragments.FilmFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;

public class EditUserDataPresenter extends FragmentPresenter{

    private final EditText editName;
    private final EditText editSruname;
    private final EditText editNickname;
    private final EditText editBio;

    public EditUserDataPresenter(EditUserDataFragment editUserDataFragment, View inflate){
        super(editUserDataFragment);

        editName = inflate.findViewById(R.id.editNameEditText);
        editSruname = inflate.findViewById(R.id.editSurnameEditText);
        editNickname = inflate.findViewById(R.id.editNicknameEditText);
        editBio = inflate.findViewById(R.id.editBioEditText);
    }

    public void setEditTextValue(){
        editName.setText(User.getInstance().getName());
        editSruname.setText(User.getInstance().getSurname());
        editNickname.setText(User.getInstance().getNickname());
        editBio.setText(User.getInstance().getBiography());
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

        DAOFactory.getUserDao().editUserData(name, surname, nickname, bio, getContext());

        if(DAOFactory.getUserDao().editUserData(name, surname, nickname, bio, getContext())){
            showSuccessMessage(getContext().getResources().getString(R.string.edit_data_success_label), getContext().getResources().getString(R.string.edit_data_success_msg));
            getFragmentManager().beginTransaction().replace(R.id.home_page_container, new FilmFragment(), FilmFragment.filmFragmentLabel).commit();
        }else {
            showErrorMessage(getContext().getResources().getString(R.string.edit_data_error_label), getContext().getResources().getString(R.string.edit_data_error_msg));
            getFragmentManager().beginTransaction().replace(R.id.home_page_container , new ProfileFragment()).commit();
        }
    }

    private boolean checkRequiredField(){
        if(isEmptyEditText(editName) || isEmptyEditText(editSruname) || isEmptyEditText(editNickname))
            return false;

        return true;
    }

    public void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    public void pressEditPassword(){

    }
}
