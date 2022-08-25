package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.FriendsPresenter;

public class FriendsFragment extends Fragment {
    private RecyclerView friendsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_friends, container, false);

        friendsRecyclerView = inflate.findViewById(R.id.friendsRecyclerView);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_friends_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.friends_list);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        FriendsPresenter friendsPresenter = new FriendsPresenter(this);
        toolbar.setNavigationOnClickListener( v -> friendsPresenter.pressBackButton() );

        return inflate;
    }

    public RecyclerView getFriendsRecyclerView() {
        return friendsRecyclerView;
    }
}