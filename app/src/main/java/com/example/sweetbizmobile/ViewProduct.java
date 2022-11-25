package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProduct extends AppCompatActivity {

    TextView itemName, itemPrice;
    ImageView itemImage;
    ExtendedFloatingActionButton buyNow, addToCart, cartIcon;
    DatabaseReference databaseReference, cartDatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;
    int amount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("imageUrl");
        String category = intent.getStringExtra("category");
        Long itemNo = intent.getLongExtra("itemNo",0);
        Long price = intent.getLongExtra("price",0);
        Long quantity = intent.getLongExtra("quantity",0);

        itemName = findViewById(R.id.ItemName);
        itemPrice = findViewById(R.id.ItemPrice);
        itemImage = findViewById(R.id.ItemImage);

        buyNow = findViewById(R.id.BuyNow);
        addToCart = findViewById(R.id.AddToCart);
        cartIcon = findViewById(R.id.cartIcon);

        user = mAuth.getCurrentUser();
        userID = user.getUid();

        String itemNoID = Long.toString(itemNo);


        databaseReference = FirebaseDatabase.getInstance().getReference("FinishedProducts");
        cartDatabase = FirebaseDatabase.getInstance().getReference("Carts").child(userID).child(itemNoID);



        itemName.setText(name);
        itemPrice.setText(Long.toString(price));
        Glide.with(this).load(imageUrl).into(itemImage);


        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProduct.this,Home.class);
                String fragment = "cartFragment";
                intent.putExtra("fragment",fragment);
                startActivity(intent);

            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CartProducts cartProducts = new CartProducts(category,imageUrl,name,itemNo,price,quantity,amount);

                cartDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            Toast.makeText(ViewProduct.this, "Item Already on Cart", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            cartDatabase.setValue(cartProducts).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(ViewProduct.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ViewProduct.this, "Failed Registration", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


    }
}