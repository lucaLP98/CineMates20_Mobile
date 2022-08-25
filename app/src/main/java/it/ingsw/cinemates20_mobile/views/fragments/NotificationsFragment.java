package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.NotificationsPresenter;

public class NotificationsFragment extends Fragment {
    public static final String notificationsFragmentLabel = "notifications_fragment";

    private RecyclerView notificationsRecycleView;
    private Button goToConnectionRequestsButton;
    private Button refreshNotificationsListFloatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationsRecycleView = inflate.findViewById(R.id.notifications_Recycle_view);
        goToConnectionRequestsButton = inflate.findViewById(R.id.goToConnectionRequestsButton);
        refreshNotificationsListFloatingActionButton = inflate.findViewById(R.id.refreshNotificationsListFloatingActionButton);

        new NotificationsPresenter(this);

        return inflate;
    }

    public RecyclerView getNotificationsRecycleView() {
        return notificationsRecycleView;
    }

    public Button getGoToConnectionRequestsButton() {
        return goToConnectionRequestsButton;
    }

    public Button getRefreshNotificationsListFloatingActionButton() {
        return refreshNotificationsListFloatingActionButton;
    }
}