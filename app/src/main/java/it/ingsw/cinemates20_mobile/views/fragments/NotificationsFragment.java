package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.NotificationsPresenter;

public class NotificationsFragment extends Fragment {
    public static final String notificationsFragmentLabel = "notifications_fragment";

    private NotificationsPresenter notificationsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationsPresenter = new NotificationsPresenter(this, inflate);

        inflate.findViewById(R.id.goToConnectionRequestsButton).setOnClickListener(v->notificationsPresenter.goToConnectionRequests());

        return inflate;
    }
}