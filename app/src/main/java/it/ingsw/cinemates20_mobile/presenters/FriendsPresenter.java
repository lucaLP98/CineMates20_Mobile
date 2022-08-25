package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.model.Friend;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.FriendsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.FriendsAdapter;

public class FriendsPresenter extends FragmentPresenter{
    private final FriendsFragment fragment;

    public FriendsPresenter(@NonNull FriendsFragment fragment) {
        super(fragment);

        this.fragment = fragment;

        showFriendsList();
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    private void showFriendsList(){
        DAOFactory.getFriendsDAO().getFriendsList(getContext(), new RequestCallback<List<Friend>>() {
            @Override
            public void onSuccess(@NonNull List<Friend> result) {
                fragment.getFriendsRecyclerView().setAdapter(new FriendsAdapter(result, getContext()));
                fragment.getFriendsRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
