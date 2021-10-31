package it.ingsw.cinemates20_mobile.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.activities.HomePresenter;

public class HomeActivity extends AppCompatActivity {
    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePresenter = new HomePresenter(getIntent().hasExtra("loginToken"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}