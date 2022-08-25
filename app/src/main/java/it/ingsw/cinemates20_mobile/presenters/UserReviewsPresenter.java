package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.UserReviewsFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.UserReviewsAdapter;

public class UserReviewsPresenter extends FragmentPresenter{
    private final UserReviewsFragment fragment;

    public UserReviewsPresenter(@NonNull UserReviewsFragment fragment){
        super(fragment);

        this.fragment = fragment;

        setReviewsRecyclerView();
    }

    private void setReviewsRecyclerView(){
        RequestCallback<List<Review>> callback = new RequestCallback<List<Review>>() {
            @Override
            public void onSuccess(@NonNull List<Review> result) {
                fragment.getReviewsRecyclerView().setAdapter(new UserReviewsAdapter(getContext(), result));
                fragment.getReviewsRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        };

        DAOFactory.getReviewDao().getUserReviews(getContext(), callback);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }
}
