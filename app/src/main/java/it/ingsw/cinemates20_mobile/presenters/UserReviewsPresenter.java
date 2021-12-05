package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.UserReviewsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.UserReviewsAdapter;

public class UserReviewsPresenter extends FragmentPresenter{
    private final RecyclerView reviewsRecyclerView;

    public UserReviewsPresenter(UserReviewsFragment fragment, @NonNull View inflate){
        super(fragment);

        reviewsRecyclerView = inflate.findViewById(R.id.userReviewsRecyclerView);
    }

    public void setReviewsRecyclerView(){
        RequestCallback<List<Review>> callback = new RequestCallback<List<Review>>() {
            @Override
            public void onSuccess(@NonNull List<Review> result) {
                reviewsRecyclerView.setAdapter(new UserReviewsAdapter(getContext(), result));
                reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        };

        DAOFactory.getReviewDao().getUserReviews(getContext(), ThisUser.getInstance().getUserID(), callback);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }
}
