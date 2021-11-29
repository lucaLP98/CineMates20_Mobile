package it.ingsw.cinemates20_mobile.presenters;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.views.fragments.UserReviewsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.ReviewsAdapter;

public class UserReviewsPresenter extends FragmentPresenter{
    private RecyclerView reviewsRecyclerView;

    public UserReviewsPresenter(UserReviewsFragment fragment, @NonNull View inflate){
        super(fragment);

        reviewsRecyclerView = inflate.findViewById(R.id.userReviewsRecyclerView);
    }

    public void setReviewsRecyclerView(){
        DAOFactory.getReviewDao().getUserReviews(getContext(), reviewsRecyclerView);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }
}
