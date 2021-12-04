package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.UsersFragment;

public class UserPresenter extends FragmentPresenter{
    private final EditText searcUsersEditText;
    private final RecyclerView resultRecyclerView;

    public UserPresenter(@NonNull UsersFragment fragment, @NonNull View inflate) {
        super(fragment);

        searcUsersEditText = inflate.findViewById(R.id.searchUserEditText);
        resultRecyclerView = inflate.findViewById(R.id.user_search_result_Recycle_view);
    }

    @Nullable
    private Integer searchWhiteSpace(@NonNull String str){
        for (int i = 0; i < str.length(); i++){
            if (Character.isWhitespace(str.charAt(i))){
                return i;
            }
        }

        return null;
    }

    public void pressSerachUsersButton(){
        if(isEmptyEditText(searcUsersEditText)){
            return;
        }

        String user = String.valueOf(searcUsersEditText.getText());
        String name, surname;

        Integer whiteSpacePosition = searchWhiteSpace(user);
        if(whiteSpacePosition != null){
            name = user.substring(0, whiteSpacePosition);
            surname = user.substring(whiteSpacePosition+1);
        }else{
            name = user;
            surname = null;
        }

        DAOFactory.getUserDao().searchUsers(getContext(), name, surname, resultRecyclerView);
    }
}
