package it.ingsw.cinemates20_mobile.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.AccessFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        getSupportFragmentManager().beginTransaction().add(R.id.mainActivityContainer, new AccessFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}