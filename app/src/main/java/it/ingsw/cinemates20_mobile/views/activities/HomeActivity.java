package it.ingsw.cinemates20_mobile.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.presenters.activities.HomePresenter;
import it.ingsw.cinemates20_mobile.views.fragments.FilmFragment;
import it.ingsw.cinemates20_mobile.views.fragments.NotificationsFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;
import it.ingsw.cinemates20_mobile.views.fragments.UsersFragment;

public class HomeActivity extends AppCompatActivity {
    private HomePresenter homePresenter;

    private Fragment filmFragment = new FilmFragment();
    private Fragment notificationsFragment = new NotificationsFragment();
    private Fragment usersFragment = new UsersFragment();
    private Fragment profileFragment = new ProfileFragment();

    private BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().add(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();

        bottomNavView = findViewById(R.id.bottom_navigation_bar_home);
        bottomNavView.setSelectedItemId(R.id.nav_movie);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment tmpFrag = null;
                String fragmentTag = null;

                switch(item.getItemId()){
                    case R.id.nav_movie:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();
                        break;

                    case R.id.nav_users:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, usersFragment).commit();
                        break;

                    case R.id.nav_notifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, notificationsFragment).commit();
                        break;

                    case R.id.nav_profile:
                        if(User.getUserAuthenticated()){
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, profileFragment).commit();
                        }else{
                            accessDeniedForNotAuthentication();
                        }
                        break;
                }

                return true;
            }
        });
    }

    private void accessDeniedForNotAuthentication(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.denied_access_label)
                .setMessage(R.string.denied_access)
                .setNegativeButton(R.string.continue_not_uthenticated, (dialog, which) -> {
                    bottomNavView.setSelectedItemId(R.id.nav_movie);
                })
                .setPositiveButton(R.string.login, (dialog, which) -> { goToAccessActivity(); })
                .show();

        getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();
    }

    private void goToAccessActivity(){
        bottomNavView.setSelectedItemId(R.id.nav_movie);
        Intent tmp = new Intent(this, MainActivity.class);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Fragment film = getSupportFragmentManager().findFragmentByTag(FilmFragment.filmFragmentLabel);

        if(film != null && film.isVisible()){
            finishAffinity();
        }else{
            bottomNavView.setSelectedItemId(R.id.nav_movie);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, filmFragment, FilmFragment.filmFragmentLabel).commit();
        }
    }
}