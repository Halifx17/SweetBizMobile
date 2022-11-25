package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mAuth = FirebaseAuth.getInstance();

        getSupportFragmentManager()
                .beginTransaction()
                        .replace(R.id.frameLayout, new StarFragment())
                                .commit();

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");

        if(fragment != null && fragment.equals("cartFragment")) {

            Log.d("Message", fragment);
            Fragment selectedFragment = new CartFragment();
            bottomNavigationView.setSelectedItemId(R.id.item4);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, selectedFragment)
                    .commit();
        }else if(fragment != null && fragment.equals("cartFragmentRefresh")) {

            Log.d("Message", fragment);
            Fragment selectedFragment = new CartFragment();
            bottomNavigationView.setSelectedItemId(R.id.item4);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, selectedFragment)
                    .commit();
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
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

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Home.this);


        builder.setMessage("Exit Application");
        builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember","false");
                editor.apply();

                Intent intent = new Intent(Home.this, MainActivity.class);
                mAuth.signOut();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Close App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                finish();
                System.exit(0);

            }
        });

        builder.show();
    }
}