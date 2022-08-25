package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.UserPresenter;

public class UsersFragment extends Fragment {
    public static final String usersFragmentLabel = "users_fragment";

    private EditText searcUsersEditText;
    private RecyclerView resultRecyclerView;
    private Button searchUsersButton;

    public EditText getSearcUsersEditText() {
        return searcUsersEditText;
    }

    public RecyclerView getResultRecyclerView() {
        return resultRecyclerView;
    }

    public Button getSearchUsersButton() {
        return searchUsersButton;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_users, container, false);

        searchUsersButton = inflate.findViewById(R.id.search_users_button);
        searcUsersEditText = inflate.findViewById(R.id.searchUserEditText);
        resultRecyclerView = inflate.findViewById(R.id.user_search_result_Recycle_view);

        new UserPresenter(this);

        return inflate;
    }
}