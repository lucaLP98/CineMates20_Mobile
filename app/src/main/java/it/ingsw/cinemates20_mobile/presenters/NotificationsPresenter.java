package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.ConnectionRequestsFragment;
import it.ingsw.cinemates20_mobile.views.fragments.NotificationsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.NotificationAdapter;

public class NotificationsPresenter extends FragmentPresenter{
    private final NotificationsFragment fragment;

    public NotificationsPresenter(@NonNull NotificationsFragment fragment) {
        super(fragment);

        this.fragment = fragment;

        showNotidficationsList();

        fragment.getGoToConnectionRequestsButton().setOnClickListener(v->goToConnectionRequests());
        fragment.getRefreshNotificationsListFloatingActionButton().setOnClickListener(v->{
            showNotidficationsList();
            ViewCompat.animate(v)
                    .rotation(360f)
                    .withLayer()
                    .setDuration(300)
                    .start();
        });
    }

    private void goToConnectionRequests(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new ConnectionRequestsFragment()).commit();
    }

    private void showNotidficationsList(){
        DAOFactory.getNotificationDAO().getNotificationsList(getContext(), new RequestCallback<List<Notification>>() {
            @Override
            public void onSuccess(@NonNull List<Notification> result) {

                result.sort((o1, o2) -> Integer.compare(o2.getNotificationID(), o1.getNotificationID()));

                fragment.getNotificationsRecycleView().setAdapter(new NotificationAdapter(getContext(), result, getFragmentManager()));
                fragment.getNotificationsRecycleView().setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
