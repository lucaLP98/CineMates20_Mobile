package it.ingsw.cinemates20_mobile.presenters;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.ConnectionRequestsFragment;

public class ConnectionRequestsPresenter extends FragmentPresenter{
    private final RecyclerView connectionRequestsRicyclerView;

    public ConnectionRequestsPresenter(@NonNull ConnectionRequestsFragment fragment, @NonNull View inflate) {
        super(fragment);

        connectionRequestsRicyclerView = inflate.findViewById(R.id.connectionRequestsRicyclerView);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    public void showConnectionRequetst(){

    }
}
