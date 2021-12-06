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
import it.ingsw.cinemates20_mobile.model.Friend;
import it.ingsw.cinemates20_mobile.model.User;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsHolder>{
    private final List<Friend> friends;
    private final Context context;

    public FriendsAdapter(List<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsAdapter.FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_row, parent, false);

        return new FriendsAdapter.FriendsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.FriendsHolder holder, int position) {
        User friend = friends.get(position);

        if(friend.getProfileImage() != null){
            Glide
                    .with(context)
                    .load(friend.getProfileImage())
                    .centerCrop()
                    .into(holder.friendImageImageView);
        }

        holder.friendNameTextView.setText(friend.getName() + " " + friend.getSurname());
        holder.friendNicknameTextView.setText(friend.getNickname());

        holder.removeFriendButton.setOnClickListener( v -> {});
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    protected static class FriendsHolder extends RecyclerView.ViewHolder{
        private final TextView friendNameTextView;
        private final TextView friendNicknameTextView;
        private final CircleImageView friendImageImageView;
        private final ImageButton removeFriendButton;

        public FriendsHolder(@NonNull View itemView) {
            super(itemView);

            friendNameTextView = itemView.findViewById(R.id.friendNameTextField);
            friendNicknameTextView = itemView.findViewById(R.id.friendNicknameTextField);
            friendImageImageView = itemView.findViewById(R.id.friendImageView);
            removeFriendButton = itemView.findViewById(R.id.removeFriendsButton);
        }
    }
}
