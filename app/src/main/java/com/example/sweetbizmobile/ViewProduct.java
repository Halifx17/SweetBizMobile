package com.example.sweetbizmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViewProduct extends AppCompatActivity {

    TextView itemName, itemPrice;
    ImageView itemImage;
    ExtendedFloatingActionButton buyNow, addToCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("imageUrl");
        Long price = intent.getLongExtra("price",0);

        itemName = findViewById(R.id.ItemName);
        itemPrice = findViewById(R.id.ItemPrice);
        itemImage = findViewById(R.id.ItemImage);

        buyNow = findViewById(R.id.BuyNow);
        addToCart = findViewById(R.id.AddToCart);

        itemName.setText(name);
        itemPrice.setText(Long.toString(price));
        Glide.with(this).load(imageUrl).into(itemImage);


    }
}