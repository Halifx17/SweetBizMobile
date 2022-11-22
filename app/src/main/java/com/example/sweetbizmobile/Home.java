package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager()
                .beginTransaction()
                        .replace(R.id.frameLayout, new StarFragment())
                                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.item1:
                        selectedFragment = new StarFragment();
                        break;
                    case R.id.item2:
                        selectedFragment = new StoreFragment();
                        break;
                    case R.id.item3:
                        selectedFragment = new MessagesFragment();
                        break;
                    case R.id.item4:
                        selectedFragment = new CartFragment();
                        break;
                    case R.id.item5:
                        selectedFragment = new ProfileFragment();
                        break;

                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, selectedFragment)
                        .commit();


                return true;
            }
        });


    }
}