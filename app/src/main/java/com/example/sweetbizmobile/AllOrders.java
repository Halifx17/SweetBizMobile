package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AllOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Orders> list;
    ArrayList<CartProducts> list2;
    ArrayList<String> orderID;
    ArrayList<String> myOrderID;
    DatabaseReference databaseReference, innerDatabaseReference,
            databaseReference2;
    AllOrdersAdapter allOrdersAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    FirebaseUser user;
    FirebaseAuth mAuth;

    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);



        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        userID = user.getUid();

        recyclerView = findViewById(R.id.recyclerAllOrders);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        orderID = new ArrayList<>();
        myOrderID = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
   //     recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        allOrdersAdapter = new AllOrdersAdapter(this,list,list2);
        recyclerView.setAdapter(allOrdersAdapter);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("OrdersUser").child(userID);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    orderID.add(dataSnapshot.getKey());
                }
                Collections.reverse(orderID);
                myOrderID = orderID;




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        databaseReference = FirebaseDatabase.getInstance().getReference("OrdersUser").child(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Orders orders = dataSnapshot.getValue(Orders.class);
                    list.add(orders);
                }
                allOrdersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        innerDatabaseReference = FirebaseDatabase.getInstance().getReference("OrderProducts").child("1669918768");

        innerDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    CartProducts cartProducts = dataSnapshot.getValue(CartProducts.class);
                    list2.add(cartProducts);
                }

                allOrdersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}