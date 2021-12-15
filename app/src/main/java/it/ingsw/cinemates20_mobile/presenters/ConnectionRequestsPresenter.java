package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.ConnectionRequestsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.ConnectionRequestsAdapter;

public class  ConnectionRequestsPresenter extends FragmentPresenter{
    private final RecyclerView connectionRequestsRicyclerView;

    public ConnectionRequestsPresenter(@NonNull ConnectionRequestsFragment fragment, @NonNull View inflate) {
        super(fragment);

        connectionRequestsRicyclerView = inflate.findViewById(R.id.connectionRequestsRicyclerView);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    public void showConnectionRequetst(){

        RequestCallback<List<ConnectionRequest>> requestCallback = new RequestCallback<List<ConnectionRequest>>() {
            @Override
            public void onSuccess(@NonNull List<ConnectionRequest> result) {
                connectionRequestsRicyclerView.setAdapter(new ConnectionRequestsAdapter(result, getContext()));
                connectionRequestsRicyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {

            }
        };

        DAOFactory.getConnectionRequestDAO().getConnectionRequests(getContext(), requestCallback);
    }
}
