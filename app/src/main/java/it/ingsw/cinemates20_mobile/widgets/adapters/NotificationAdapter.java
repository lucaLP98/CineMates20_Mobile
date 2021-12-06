package it.ingsw.cinemates20_mobile.widgets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{
    private final Context context;
    private final List<Notification> notifications;


    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_row, parent, false);

        return new NotificationAdapter.NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.notificationBodyTextView.setText(notifications.get(position).getNotificationText());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public static class NotificationHolder extends RecyclerView.ViewHolder {
        private final TextView notificationBodyTextView;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            notificationBodyTextView = itemView.findViewById(R.id.notificationBodyTextView);
        }
    }
}
