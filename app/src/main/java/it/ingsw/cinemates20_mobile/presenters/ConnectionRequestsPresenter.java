package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.ConnectionRequestsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.ConnectionRequestsAdapter;

public class  ConnectionRequestsPresenter extends FragmentPresenter{
    private final ConnectionRequestsFragment fragment;

    public ConnectionRequestsPresenter(@NonNull ConnectionRequestsFragment fragment) {
        super(fragment);

        this.fragment = fragment;

        showConnectionRequests();
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    private void showConnectionRequests(){

        RequestCallback<List<ConnectionRequest>> requestCallback = new RequestCallback<List<ConnectionRequest>>() {
            @Override
            public void onSuccess(@NonNull List<ConnectionRequest> result) {
                fragment.getConnectionRequestsRicyclerView().setAdapter(new ConnectionRequestsAdapter(result, getContext()));
                fragment.getConnectionRequestsRicyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {

            }
        };

        DAOFactory.getConnectionRequestDAO().getConnectionRequests(getContext(), requestCallback);
    }
}
