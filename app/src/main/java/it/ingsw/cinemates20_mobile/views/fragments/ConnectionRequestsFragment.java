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
import it.ingsw.cinemates20_mobile.presenters.ConnectionRequestsPresenter;

public class ConnectionRequestsFragment extends Fragment {
    private RecyclerView connectionRequestsRicyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_connection_requests, container, false);

        connectionRequestsRicyclerView = inflate.findViewById(R.id.connectionRequestsRicyclerView);

        Toolbar toolbar = inflate.findViewById(R.id.connection_request_fragment_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.connection_requests);
        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        ConnectionRequestsPresenter connectionRequestsPresenter = new ConnectionRequestsPresenter(this);
        toolbar.setNavigationOnClickListener( v -> connectionRequestsPresenter.pressBackButton() );

        return inflate;
    }

    public RecyclerView getConnectionRequestsRicyclerView() {
        return connectionRequestsRicyclerView;
    }
}