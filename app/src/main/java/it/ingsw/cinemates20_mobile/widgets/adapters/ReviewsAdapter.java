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

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.User;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>{
    private final Context context;
    private final List<Review> reviews;

    public ReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_row, parent, false);

        return new ReviewsAdapter.ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsHolder holder, int position) {
        setThisUser(holder, position);
        holder.reviewBodyTextView.setText(reviews.get(position).getReviewText());
        holder.reviewVoteTextView.setText(String.valueOf(reviews.get(position).getReviewVote()));
        //holder.rowLayout.setOnClickListener( v -> );
    }

    @Override
    public int getItemCount() { return reviews.size(); }

    protected static  class ReviewsHolder extends RecyclerView.ViewHolder {

        private final TextView userNicknameTextView;
        private final TextView reviewBodyTextView;
        private final CircleImageView userImageProfileImageView;
        private final FrameLayout rowLayout;
        private final TextView reviewVoteTextView;

        public ReviewsHolder(@NonNull View itemView) {
            super(itemView);
            userNicknameTextView = itemView.findViewById(R.id.userReviewNicknameTextView);
            reviewBodyTextView = itemView.findViewById(R.id.userReviewTextView);
            userImageProfileImageView = itemView.findViewById(R.id.userReviewPhotoImageView);
            reviewVoteTextView = itemView.findViewById(R.id.reviewVoteTextView);

            rowLayout = itemView.findViewById(R.id.review_row_layout);
        }
    }

    private void setThisUser(@NonNull ReviewsHolder holder, int position){
        holder.userNicknameTextView.setText(User.getInstance().getNickname());
        if(User.getInstance().getUriProfileImage() != null){
            Glide
                    .with(context)
                    .load(User.getInstance().getUriProfileImage())
                    .centerCrop()
                    .into(holder.userImageProfileImageView);
        }
    }
}
