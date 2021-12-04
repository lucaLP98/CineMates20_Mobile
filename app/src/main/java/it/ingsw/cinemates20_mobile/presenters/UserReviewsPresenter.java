package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.views.fragments.UserReviewsFragment;

public class UserReviewsPresenter extends FragmentPresenter{
    private final RecyclerView reviewsRecyclerView;

    public UserReviewsPresenter(UserReviewsFragment fragment, @NonNull View inflate){
        super(fragment);

        reviewsRecyclerView = inflate.findViewById(R.id.userReviewsRecyclerView);
    }

    public void setReviewsRecyclerView(){
        DAOFactory.getReviewDao().getUserReviews(getContext(), ThisUser.getInstance().getUserID(), reviewsRecyclerView);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }
}
