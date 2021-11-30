package it.ingsw.cinemates20_mobile.widgets.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Review;

public class UserReviewsAdapter extends RecyclerView.Adapter<UserReviewsAdapter.UserReviewsHolder>{
    private final Context context;

    public UserReviewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_review_row, parent, false);

        return new UserReviewsAdapter.UserReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected static  class UserReviewsHolder extends RecyclerView.ViewHolder{
        protected final TextView movieNameTextView;
        protected final TextView reviewVoteTextView;
        protected final TextView reviewBodyTextView;
        protected final ImageView moviePoster;

        public UserReviewsHolder(@NonNull View itemView) {
            super(itemView);

            movieNameTextView = itemView.findViewById(R.id.movieTitleUserReviewListVoteTextView);
            reviewVoteTextView = itemView.findViewById(R.id.movieRatedUserReviewListTextView);
            reviewBodyTextView = itemView.findViewById(R.id.userReviewDescriptionUserReviewsListTextView);
            moviePoster = itemView.findViewById(R.id.moviePosterUserReviewListImageView);
        }
    }
}
