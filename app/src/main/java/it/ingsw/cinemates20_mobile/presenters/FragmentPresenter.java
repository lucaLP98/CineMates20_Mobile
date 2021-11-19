package it.ingsw.cinemates20_mobile.presenters;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import it.ingsw.cinemates20_mobile.R;

public abstract class FragmentPresenter {
    private final FragmentManager fragmentManager;
    private final Context context;

    public FragmentPresenter(@NonNull Fragment fragment){
        fragmentManager = fragment.getParentFragmentManager();
        context = fragment.getContext();
    }

    public Context getContext(){ return context; }

    public FragmentManager getFragmentManager(){ return fragmentManager; }

    public void showErrorMessage(String title, String errorMsg){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setMessage(errorMsg)
                .setNegativeButton("OK", (dialog, which) -> {})
                .show();
    }

    public void showSuccessMessage(String title, String successMsg){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setIcon(R.drawable.ic_baseline_done_outline_24)
                .setMessage(successMsg)
                .setPositiveButton("OK", (dialog, which) -> {})
                .show();
    }

    public boolean isEmptyEditText(@NonNull EditText editText){
        return String.valueOf(editText.getText()).equals("");
    }
}
