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
import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.model.User;

public class ConnectionRequestsAdapter extends RecyclerView.Adapter<ConnectionRequestsAdapter.ConnectionRequestsHolder>{
    private final List<ConnectionRequest> connectionRequestList;
    private final Context context;

    public ConnectionRequestsAdapter(List<ConnectionRequest> connectionRequestList, Context context) {
        this.connectionRequestList = connectionRequestList;
        this.context = context;
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

        holder.acceptButton.setOnClickListener( v -> respondToRequest(connectionRequestList.get(position).getConnectionRequestID(), userSender.getUserID(), true, position) );
        holder.declineButton.setOnClickListener( v -> respondToRequest(connectionRequestList.get(position).getConnectionRequestID(), userSender.getUserID(), false, position) );
    }

    private void respondToRequest(int requestID, String senderID, boolean response, int position){
        DAOFactory.getConnectionRequestDAO().respondToConnectionRequest(context, requestID, senderID, response);
        connectionRequestList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return connectionRequestList.size();
    }

    public static class ConnectionRequestsHolder extends RecyclerView.ViewHolder{
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
