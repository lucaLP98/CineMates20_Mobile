package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.UsersFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.UsersAdapter;

public class UserPresenter extends FragmentPresenter{
    private final UsersFragment fragment;

    public UserPresenter(@NonNull UsersFragment fragment) {
        super(fragment);

        this.fragment = fragment;

        fragment.getSearchUsersButton().setOnClickListener( v -> pressSerachUsersButton() );
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

    private void pressSerachUsersButton(){
        if(isEmptyEditText(fragment.getSearcUsersEditText())){
            return;
        }

        String user = String.valueOf(fragment.getSearcUsersEditText().getText());
        String name, surname;

        Integer whiteSpacePosition = searchWhiteSpace(user);
        if(whiteSpacePosition != null){
            name = user.substring(0, whiteSpacePosition);
            surname = user.substring(whiteSpacePosition+1);
        }else{
            name = user;
            surname = null;
        }

        RequestCallback<List<User>> callback = new RequestCallback<List<User>>() {
            @Override
            public void onSuccess(@NonNull List<User> result) {
                fragment.getResultRecyclerView().setAdapter(new UsersAdapter(getContext(), result));
                fragment.getResultRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        };

        DAOFactory.getUserDao().searchUsers(getContext(), name, surname, callback);
    }
}
