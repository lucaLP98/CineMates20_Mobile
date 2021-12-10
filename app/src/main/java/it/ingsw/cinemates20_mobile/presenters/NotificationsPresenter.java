package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.Comparator;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.ConnectionRequestsFragment;
import it.ingsw.cinemates20_mobile.views.fragments.NotificationsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.NotificationAdapter;

public class NotificationsPresenter extends FragmentPresenter{
    private final RecyclerView notificationsRecycleView;

    public NotificationsPresenter(@NonNull NotificationsFragment fragment, @NonNull View inflate) {
        super(fragment);

        notificationsRecycleView = inflate.findViewById(R.id.notifications_Recycle_view);
    }

    public void goToConnectionRequests(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new ConnectionRequestsFragment()).commit();
    }

    public void showNotidficationsList(){
        DAOFactory.getNotificationDAO().getNotificationsList(getContext(), new RequestCallback<List<Notification>>() {
            @Override
            public void onSuccess(@NonNull List<Notification> result) {

                result.sort(new Comparator<Notification>() {
                    @Override
                    public int compare(Notification o1, Notification o2) {
                        int result;

                        if (o1.getNotificationID() < o2.getNotificationID()) result = 1;
                        else if (o1.getNotificationID() == o2.getNotificationID()) result = 0;
                        else result = -1;

                        return result;
                    }
                });

                notificationsRecycleView.setAdapter(new NotificationAdapter(getContext(), result, getFragmentManager()));
                notificationsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
