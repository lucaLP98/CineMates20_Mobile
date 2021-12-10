package it.ingsw.cinemates20_mobile.presenters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.VolleyError;

import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Friend;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public class ShareMoviePresenter {
    private final Movie movie;
    private final Context context;
    private final Spinner friendsSpinner;

    public ShareMoviePresenter(Context context, Movie movie){
        this.movie = movie;
        this.context = context;
        friendsSpinner = new Spinner(context);
    }

    public void shareMovie(){
        DAOFactory.getFriendsDAO().getFriendsList(context, new RequestCallback<List<Friend>>() {
            @Override
            public void onSuccess(@NonNull List<Friend> result) {
                setUpFriendSpinner(result);
                showDialogShareMovie();
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    private void setUpFriendSpinner(List<Friend> friends){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(20, 10,20, 10);

        friendsSpinner.setBackground(context.getResources().getDrawable(R.drawable.textfield_rounded_corner, null));
        friendsSpinner.setLayoutParams(params);

        ArrayAdapter<Friend> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, friends);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendsSpinner.setAdapter(adapter);
    }

    @NonNull
    private View getShareMovieDialogLayout(){
        LinearLayout layout = new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(friendsSpinner);

        return layout;
    }

    private void showErrorDialog(){
        new AlertDialog.Builder(context)
                .setTitle(R.string.error_share_label)
                .setMessage(R.string.error_share)
                .setIcon(R.drawable.ic_baseline_error_outline_24)
                .setNegativeButton("OK", (dialog, which) -> {})
                .show();
    }

    private void showDialogShareMovie(){
        RequestCallback<String> callback = new RequestCallback<String>() {
            @Override
            public void onSuccess(@NonNull String result) {
                Log.d("ShareMovie", "Shareing Movie success");
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                showErrorDialog();
            }
        };

        new AlertDialog.Builder(context)
                .setTitle(R.string.share_label)
                .setView(getShareMovieDialogLayout())
                .setPositiveButton(R.string.share,
                        (dialog, which) -> {
                            Friend friend = (Friend) friendsSpinner.getSelectedItem();
                            DAOFactory.getSharingMovieDAO().shareFilm(context, movie, friend.getUserID(), callback);
                        })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {})
                .show();
    }
}
