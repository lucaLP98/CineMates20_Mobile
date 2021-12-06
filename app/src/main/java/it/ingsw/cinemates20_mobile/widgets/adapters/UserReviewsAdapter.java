package it.ingsw.cinemates20_mobile.widgets.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.presenters.EditReviewPresenter;

public class UserReviewsAdapter extends RecyclerView.Adapter<UserReviewsAdapter.UserReviewsHolder>{
    private final Context context;
    private final List<Review> reviews;

    public UserReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public UserReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_review_row, parent, false);

        return new UserReviewsAdapter.UserReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewsHolder holder, int position) {
        holder.movieNameTextView.setText(reviews.get(position).getFilmTitle());
        holder.reviewBodyTextView.setText(reviews.get(position).getReviewText());
        holder.reviewVoteTextView.setText(String.valueOf(reviews.get(position).getReviewVote()));
        if(reviews.get(position).getFilmPosterUri() != null){
            Glide
                    .with(context)
                    .load(Uri.parse(reviews.get(position).getFilmPosterUri()))
                    .centerCrop()
                    .into(holder.moviePoster);
        }

        holder.optionReviewImageButton.setOnClickListener(v->showReviewPopUpMenu(v, reviews.get(position), position));
    }

    private void removeReview(Review review, int position){
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_review)
                .setMessage(R.string.delete_review_msg)
                .setPositiveButton(R.string.confirm,
                        (dialog, which) -> {
                            DAOFactory.getReviewDao().deleteUserReviews(context, review.getReviewID());
                            reviews.remove(review);
                            notifyItemRemoved(position);
                        })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {})
                .show();
    }

    private void editReview(@NonNull Review review, int position){
        EditReviewPresenter editReviewPresenter = new EditReviewPresenter(review, context);

        new AlertDialog.Builder(context)
                .setTitle(R.string.edit_review)
                .setView(editReviewPresenter.getEditReviewsDialogLayout())
                .setPositiveButton(R.string.confirm,
                        (dialog, which) -> {
                            if(editReviewPresenter.pressSaveChangesReviewbutton()) notifyItemChanged(position);
                        })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {})
                .show();
    }

    private void showReviewPopUpMenu(View trigger, Review review, int position){
        PopupMenu popupMenu = new PopupMenu(context, trigger);
        popupMenu.getMenuInflater().inflate(R.menu.user_review_pop_up_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            boolean ret;

            switch (item.getItemId()){
                case R.id.edit_review_popup_menu:
                    editReview(review, position);
                    ret = true;
                    break;

                case R.id.delete_review_popup_menu:
                    removeReview(review, position);
                    ret = true;
                    break;

                default: ret = false; break;
            }
            return ret;
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    protected static class UserReviewsHolder extends RecyclerView.ViewHolder{
        protected final TextView movieNameTextView;
        protected final TextView reviewVoteTextView;
        protected final TextView reviewBodyTextView;
        protected final ImageView moviePoster;
        protected final ImageView optionReviewImageButton;

        public UserReviewsHolder(@NonNull View itemView) {
            super(itemView);

            movieNameTextView = itemView.findViewById(R.id.movieTitleUserReviewListVoteTextView);
            reviewVoteTextView = itemView.findViewById(R.id.movieRatedUserReviewListTextView);
            reviewBodyTextView = itemView.findViewById(R.id.userReviewDescriptionUserReviewsListTextView);
            moviePoster = itemView.findViewById(R.id.moviePosterUserReviewListImageView);
            optionReviewImageButton = itemView.findViewById(R.id.user_movie_review_option_row);
        }
    }
}
