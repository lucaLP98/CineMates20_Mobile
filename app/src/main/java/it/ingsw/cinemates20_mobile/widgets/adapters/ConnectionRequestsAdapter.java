package it.ingsw.cinemates20_mobile.widgets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.presenters.ConnectionRequestsPresenter;

public class ConnectionRequestsAdapter extends RecyclerView.Adapter<ConnectionRequestsAdapter.ConnectionRequestsHolder>{
    private final List<ConnectionRequest> connectionRequestList;
    private final Context context;
    private final ConnectionRequestsPresenter connectionRequestsPresenter;

    public ConnectionRequestsAdapter(List<ConnectionRequest> connectionRequestList, Context context, ConnectionRequestsPresenter connectionRequestsPresenter) {
        this.connectionRequestList = connectionRequestList;
        this.context = context;
        this.connectionRequestsPresenter = connectionRequestsPresenter;
    }

    @NonNull
    @Override
    public ConnectionRequestsAdapter.ConnectionRequestsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.connection_request_row, parent, false);

        return new ConnectionRequestsAdapter.ConnectionRequestsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionRequestsAdapter.ConnectionRequestsHolder holder, int position) {
        User userSender = connectionRequestList.get(position).getUserSender();

        if(userSender.getProfileImage() != null){
            Glide
                    .with(context)
                    .load(userSender.getProfileImage())
                    .centerCrop()
                    .into(holder.profileImageImageView);
        }

        holder.nameTextView.setText(userSender.getName() + " " + userSender.getSurname());
        holder.nicknameTextView.setText(userSender.getNickname());
    }

    @Override
    public int getItemCount() {
        return connectionRequestList.size();
    }

    public class ConnectionRequestsHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;
        private final TextView nicknameTextView;
        private final CircleImageView profileImageImageView;
        private final ImageButton acceptButton;
        private final ImageButton declineButton;

        public ConnectionRequestsHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.userNameRequestTextField);
            nicknameTextView = itemView.findViewById(R.id.userNicknameRequestTextField);
            profileImageImageView = itemView.findViewById(R.id.userImageRequestImageView);
            acceptButton = itemView.findViewById(R.id.acceptConnectionRequestButton);
            declineButton = itemView.findViewById(R.id.DeclineConnectionRequestButton);
        }
    }
}
