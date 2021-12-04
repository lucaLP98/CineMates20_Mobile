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
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.model.User;

public class UsersAdapter  extends RecyclerView.Adapter<UsersAdapter.UsersHolder>{
    private final List<User> users;
    private final Context context;

    public UsersAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_row, parent, false);

        return new UsersAdapter.UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        User user = users.get(position);
        if(user.getProfileImage() != null){
            Glide
                    .with(context)
                    .load(user.getProfileImage())
                    .centerCrop()
                    .into(holder.userImageImageView);
        }

        holder.userNameTextView.setText(user.getName() + " " + user.getSurname());
        holder.userNicknameTextView.setText(user.getNickname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    protected class UsersHolder extends RecyclerView.ViewHolder {
        private final TextView userNameTextView;
        private final TextView userNicknameTextView;
        private final CircleImageView userImageImageView;
        private final ImageButton addUserButton;

        public UsersHolder(@NonNull View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.userNamePreviewTextField);
            userNicknameTextView = itemView.findViewById(R.id.userNicknamePreviewTextField);
            userImageImageView = itemView.findViewById(R.id.userImagePreviewImageView);
            addUserButton = itemView.findViewById(R.id.addNewFriendsButton);
        }
    }
}
