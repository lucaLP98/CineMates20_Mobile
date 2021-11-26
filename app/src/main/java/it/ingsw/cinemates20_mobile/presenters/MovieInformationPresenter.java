package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.views.fragments.MovieInformationFragment;

public class MovieInformationPresenter extends FragmentPresenter{

    public MovieInformationPresenter(@NonNull MovieInformationFragment fragment, View inflate) {
        super(fragment);
    }

    public void pressBackButton(){ getFragmentManager().popBackStack(); }
}
