package it.ingsw.cinemates20_mobile.widgets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.views.fragments.MovieInformationFragment;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{
    private final Context context;
    private final List<Notification> notifications;
    private final FragmentManager fragmentManager;

    public NotificationAdapter(Context context, List<Notification> notifications, FragmentManager fragmentManager) {
        this.context = context;
        this.notifications = notifications;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_row, parent, false);

        return new NotificationAdapter.NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.notificationBodyTextView.setText(notification.getNotificationText());

        if(notification.getType() == Notification.notificationTypeEnum.SHARING){
            holder.rowLayout.setOnClickListener( v -> viewMovieShared(notification.getMovieID()));
        }
    }

    private void viewMovieShared(int movieID){
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new MovieInformationFragment(movieID)).commit();
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public static class NotificationHolder extends RecyclerView.ViewHolder {
        private final TextView notificationBodyTextView;
        private final FrameLayout rowLayout;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            notificationBodyTextView = itemView.findViewById(R.id.notificationBodyTextView);
            rowLayout = itemView.findViewById(R.id.notification_row_layout);
        }
    }
}
