package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewProduct extends AppCompatActivity {

    TextView itemName,
            itemPrice,
            itemDescription,
            itemQuantity,
            textViewTotalQuantity,
            textViewImageURL,
            textViewCategory,
            textViewItemNo,
            textViewPrice;
    ImageView itemImage;
    ExtendedFloatingActionButton buyNow, addToCart, cartIcon;
    DatabaseReference databaseReference, cartDatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID, keys, prodCategory, prodDescription, prodImageURL, prodItemNo, prodName, prodPrice, prodTotalQuantity, prodLastUpdated;
    Long totalPrice;
    Button addBtn, minusBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mAuth = FirebaseAuth.getInstance();

        addBtn = findViewById(R.id.addBtn);
        minusBtn = findViewById(R.id.minusBtn);

        Intent intent = getIntent();
        Long itemNo = intent.getLongExtra("itemNo",0);


        textViewCategory = findViewById(R.id.textViewCategory);
        itemDescription = findViewById(R.id.itemDescription);
        textViewImageURL = findViewById(R.id.textViewimageURL);
        textViewItemNo = findViewById(R.id.textViewItemNo);
        itemName = findViewById(R.id.ItemName);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewTotalQuantity = findViewById(R.id.totalQuantity);

        itemPrice = findViewById(R.id.ItemPrice);
        itemImage = findViewById(R.id.ItemImage);
        itemQuantity = findViewById(R.id.itemQuantity);
        itemQuantity.setText("1");

        textViewCategory.setVisibility(View.INVISIBLE);
        textViewImageURL.setVisibility(View.INVISIBLE);
        textViewItemNo.setVisibility(View.INVISIBLE);
        textViewTotalQuantity.setVisibility(View.INVISIBLE);






        buyNow = findViewById(R.id.BuyNow);
        addToCart = findViewById(R.id.AddToCart);
        cartIcon = findViewById(R.id.cartIcon);

        user = mAuth.getCurrentUser();
        userID = user.getUid();

        String itemNoID = Long.toString(itemNo);


        databaseReference = FirebaseDatabase.getInstance().getReference("FinishedProducts");
        cartDatabase = FirebaseDatabase.getInstance().getReference("Carts").child(userID).child(itemNoID);


        Query userQuery = databaseReference.orderByChild("itemno").equalTo(itemNo);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    keys = dataSnapshot.getKey();
                    Products products = dataSnapshot.getValue(Products.class);

                    prodCategory = products.getCategory();
                    prodDescription = products.getDescription();
                    prodImageURL = products.getImageURL();
                    prodItemNo = Long.toString(products.getItemno());
                    prodName = products.getName();
                    prodPrice = Long.toString(products.getPrice());
                    prodTotalQuantity = Long.toString(products.getQuantity());



                    textViewCategory.setText(prodCategory);
                    itemDescription.setText(prodDescription);
                    textViewImageURL.setText(prodImageURL);
                    textViewItemNo.setText(prodItemNo);

                    itemName.setText(prodName);
                    textViewPrice.setText(prodPrice);
                    textViewTotalQuantity.setText(prodTotalQuantity);

                    itemPrice.setText("\u20B1"+prodPrice+"/Piece");
                    Glide.with(getApplicationContext()).load(prodImageURL).into(itemImage);


                    Log.d("TEST", keys+" "+prodName+" "+prodPrice);




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProduct.this,Home.class);
                String fragment = "cartFragment";
                intent.putExtra("fragment",fragment);
                startActivity(intent);

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long totalQuantity = Long.parseLong(textViewTotalQuantity.getText().toString());
                Long myQuantity = Long.parseLong(itemQuantity.getText().toString());
                Long newQuantity;

                if(myQuantity<totalQuantity){
                    newQuantity = myQuantity + 1;
                    itemQuantity.setText(Long.toString(newQuantity));
                }else{
                    Toast.makeText(getApplicationContext(),textViewTotalQuantity.getText().toString()+" is the maximum amount",Toast.LENGTH_SHORT).show();
                }




            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long totalQuantity = Long.parseLong(textViewTotalQuantity.getText().toString());
                Long myQuantity = Long.parseLong(itemQuantity.getText().toString());
                Long newQuantity;

                if(myQuantity!=1){
                    newQuantity = myQuantity - 1;
                    itemQuantity.setText(Long.toString(newQuantity));
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot go below one",Toast.LENGTH_SHORT).show();
                }

            }
        });





        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = itemName.getText().toString();
                String category = textViewCategory.getText().toString();
                String imageUrl = textViewImageURL.getText().toString();
                Long price = Long.parseLong(textViewPrice.getText().toString());
                Long totalQuantity = Long.parseLong(textViewTotalQuantity.getText().toString());
                Integer quantity = Integer.parseInt(itemQuantity.getText().toString());
                totalPrice = price * quantity;




                CartProducts cartProducts = new CartProducts(category,imageUrl,name,itemNo,price,totalQuantity,totalPrice,quantity);




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