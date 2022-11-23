package com.example.sweetbizmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewProduct extends AppCompatActivity {

    TextView itemName, itemPrice;
    ImageView itemImage;
    ExtendedFloatingActionButton buyNow, addToCart;
    DatabaseReference databaseReference, userDatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("imageUrl");
        Long price = intent.getLongExtra("price",0);

        itemName = findViewById(R.id.ItemName);
        itemPrice = findViewById(R.id.ItemPrice);
        itemImage = findViewById(R.id.ItemImage);

        buyNow = findViewById(R.id.BuyNow);
        addToCart = findViewById(R.id.AddToCart);

        user = mAuth.getCurrentUser();
        userID = user.getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("FinishedProducts");
        userDatabase = FirebaseDatabase.getInstance().getReference("Cart").child(userID);



        itemName.setText(name);
        itemPrice.setText(Long.toString(price));
        Glide.with(this).load(imageUrl).into(itemImage);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}