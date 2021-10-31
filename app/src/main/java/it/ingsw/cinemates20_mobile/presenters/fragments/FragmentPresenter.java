package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import it.ingsw.cinemates20_mobile.R;

public abstract class FragmentPresenter {
    private final FragmentManager fragmentManager;
    private final Context context;

    public FragmentPresenter(Fragment fragment){
        fragmentManager = fragment.getParentFragmentManager();
        context = fragment.getContext();
    }

    public Context getContext(){ return context; }

    public FragmentManager getFragmentManager(){ return fragmentManager; }

    public void showErrorMessage(String title, String errorMsg){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(errorMsg)
                .setNegativeButton("OK", (dialog, which) -> {})
                .show();
    }

    public void showSuccessMessage(String title, String successMsg){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(successMsg)
                .setPositiveButton("OK", (dialog, which) -> {})
                .show();
    }
}
