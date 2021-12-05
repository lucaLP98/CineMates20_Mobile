package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.ConnectionRequestsFragment;
import it.ingsw.cinemates20_mobile.views.fragments.NotificationsFragment;

public class NotificationsPresenter extends FragmentPresenter{
    private RecyclerView notificationsRecycleView;

    public NotificationsPresenter(@NonNull NotificationsFragment fragment, @NonNull View inflate) {
        super(fragment);

        notificationsRecycleView = inflate.findViewById(R.id.notifications_Recycle_view);
    }

    public void goToConnectionRequests(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new ConnectionRequestsFragment()).commit();
    }

    public void showNotidficationsList(){

    }
}
