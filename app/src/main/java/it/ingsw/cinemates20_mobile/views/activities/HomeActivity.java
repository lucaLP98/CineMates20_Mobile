package it.ingsw.cinemates20_mobile.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.FilmFragment;
import it.ingsw.cinemates20_mobile.views.fragments.NotificationsFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;
import it.ingsw.cinemates20_mobile.views.fragments.UsersFragment;

public class HomeActivity extends AppCompatActivity {
    private final Fragment filmFragment;
    private final Fragment notificationsFragment;
    private final Fragment usersFragment;
    private final Fragment profileFragment;

    private BottomNavigationView bottomNavView;

    public HomeActivity(){
        super();

        filmFragment = new FilmFragment();
        notificationsFragment = new NotificationsFragment();
        usersFragment = new UsersFragment();
        profileFragment = new ProfileFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().add(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();

        bottomNavView = findViewById(R.id.bottom_navigation_bar_home);
        bottomNavView.setSelectedItemId(R.id.nav_movie);

        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_movie:
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();
                    break;

                case R.id.nav_users:
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, usersFragment, UsersFragment.usersFragmentLabel).commit();
                    break;

                case R.id.nav_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, notificationsFragment, NotificationsFragment.notificationsFragmentLabel).commit();
                    break;

                case R.id.nav_profile:
                    if(User.getUserAuthenticated()){
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, profileFragment, ProfileFragment.profileFragmentLabel).commit();
                    }else{
                        accessDeniedForNotAuthentication();
                    }
                    break;
            }

            return true;
        });
    }

    private void accessDeniedForNotAuthentication(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.denied_access_label)
                .setMessage(R.string.denied_access)
                .setNegativeButton(R.string.continue_not_uthenticated, (dialog, which) -> bottomNavView.setSelectedItemId(R.id.nav_movie))
                .setPositiveButton(R.string.login, (dialog, which) -> goToAccessActivity())
                .show();

        getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();
    }

    private void goToAccessActivity(){
        bottomNavView.setSelectedItemId(R.id.nav_movie);
        Intent tmp = new Intent(this, MainActivity.class);
        startActivity(tmp);
    }

    private final boolean[] doubleBackToExitPressedOnce = {false};

    @Override
    public void onBackPressed() {
        Fragment film = getSupportFragmentManager().findFragmentByTag(FilmFragment.filmFragmentLabel);

        if(film != null && film.isVisible()){
            if (doubleBackToExitPressedOnce[0]) {
                if(User.getUserAuthenticated()){
                    CognitoSettings.getInstance(this).getUserPool().getUser(User.getInstance().getEmail()).signOut();
                }
                finishAffinity();
            }
            doubleBackToExitPressedOnce[0] = true;
            Toast.makeText(this, R.string.double_click_to_exit, Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce[0] = false, 2000);

        }else{
            bottomNavView.setSelectedItemId(R.id.nav_movie);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();
        }
    }
}