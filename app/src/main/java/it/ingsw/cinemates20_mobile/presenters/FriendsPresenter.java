package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Friend;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.FriendsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.FriendsAdapter;

public class FriendsPresenter extends FragmentPresenter{

    private final RecyclerView friendsRecyclerView;

    public FriendsPresenter(@NonNull FriendsFragment fragment, @NonNull View inflate) {
        super(fragment);

        friendsRecyclerView = inflate.findViewById(R.id.friendsRecyclerView);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    public void showFriendsList(){
        DAOFactory.getFriendsDAO().getFriendsList(getContext(), new RequestCallback<List<Friend>>() {
            @Override
            public void onSuccess(@NonNull List<Friend> result) {
                friendsRecyclerView.setAdapter(new FriendsAdapter(result, getContext()));
                friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
