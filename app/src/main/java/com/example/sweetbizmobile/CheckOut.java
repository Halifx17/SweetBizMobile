package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CheckOut extends AppCompatActivity{

    RecyclerView recyclerView;
    ArrayList<CartProducts> list;
    DatabaseReference databaseReference, fromPathDatabaseReference,
            toPathDatabaseReference, deletePathDatabaseReference,
            orderAdminDatabaseReference, orderUserDatabaseReference,
            updateQuantityDatabaseReference;
    CheckOutAdapter checkOutAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    FirebaseUser user;
    FirebaseAuth mAuth;

    String userID, userName;

    ExtendedFloatingActionButton placeOrderBtn;

    String stringCheckOutID, stringCheckOutPrice, myKey;
    TextView checkOutTotalPrice;

    DateFormat dateFormat;
    String stringDate;
    Long longDate;
    Long trimLongDate;

    ArrayList<String> checkOutID = new ArrayList<>();
    ArrayList<String> checkOutPrice = new ArrayList<>();
    ArrayList<String> checkOutNames = new ArrayList<>();
    ArrayList<String> checkOutAmount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Bundle bundle = getIntent().getExtras();
        checkOutID = bundle.getStringArrayList("checkOutID");
        checkOutPrice = bundle.getStringArrayList("checkOutPrice");
        checkOutNames = bundle.getStringArrayList("checkOutNames");
        checkOutAmount = bundle.getStringArrayList("checkOutAmount");
        Log.d("MyArrayID", checkOutID.toString());
        Log.d("MyArrayPrice", checkOutPrice.toString());
        Log.d("MyArrayNames", checkOutNames.toString());
        Log.d("MyArrayAmount", checkOutAmount.toString());

        checkOutTotalPrice = findViewById(R.id.checkOutProductTotalPrice);


        placeOrderBtn = findViewById(R.id.placeOrderBtn);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        userID = user.getUid();
        userName = user.getEmail();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerCheckOut);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        checkOutAdapter = new CheckOutAdapter(getApplication(),list);
        recyclerView.setAdapter(checkOutAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(userID);




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    for(int i = checkOutID.size()-1; i>= 0; i--){
                    if(dataSnapshot.getKey().equals(checkOutID.get(i))){
                    CartProducts cartProducts = dataSnapshot.getValue(CartProducts.class);
                    list.add(cartProducts);
                    }
                    }
                }
                checkOutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        int size = checkOutPrice.size();
        int [] totalPriceArray = new int [size];
        for(int i=0; i<size; i++) {
            totalPriceArray[i] = Integer.parseInt(checkOutPrice.get(i));
        }

        int totalSumOfPrice=0;
        for(int i=0; i< totalPriceArray.length; i++) {
            totalSumOfPrice += totalPriceArray[i];
        }

        checkOutTotalPrice.setText(Integer.toString(totalSumOfPrice));

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateFormat = new SimpleDateFormat("d-MMM-yyyy h:mm a");
                stringDate = dateFormat.format(Calendar.getInstance().getTime());
                longDate = new Date().getTime();
                trimLongDate = longDate/1000;

                copyRecord();
                placeOrder();
                updateQuantity();
                Intent intent = new Intent(getApplicationContext(),OrderPlaced.class);
                getApplication().startActivity(intent);

            }
        });



        //MMM-d-yyyy h:mm a





    }

    private void placeOrder() {

        int size = checkOutPrice.size();
        int [] totalPriceArray = new int [size];
        for(int i=0; i<size; i++) {
            totalPriceArray[i] = Integer.parseInt(checkOutPrice.get(i));
        }

        int totalSumOfPrice=0;
        for(int i=0; i< totalPriceArray.length; i++) {
            totalSumOfPrice += totalPriceArray[i];
        }

        int finalTotalSumOfPrice = totalSumOfPrice;

        orderUserDatabaseReference = FirebaseDatabase.getInstance().getReference("OrdersUser").child(userID).child(Long.toString(trimLongDate));
        orderAdminDatabaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(Long.toString(trimLongDate));

        String productNames = checkOutNames.toString();
        String finalProductNames = productNames.substring(1,productNames.length()-1);

        Orders orders = new Orders(userName,stringDate,finalProductNames,"pending",trimLongDate,finalTotalSumOfPrice);
        OrdersAdmin ordersAdmin = new OrdersAdmin(userName,stringDate,finalProductNames,"pending",userID,trimLongDate,finalTotalSumOfPrice);

        orderUserDatabaseReference.setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Order Unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

        orderAdminDatabaseReference.setValue(ordersAdmin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Order Unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*

        orderAdminDatabaseReference.push(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Order Unsuccessful",Toast.LENGTH_SHORT).show();
                }



            }
        });

         */






    }

    private void copyRecord() {

        fromPathDatabaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(userID);
        toPathDatabaseReference = FirebaseDatabase.getInstance().getReference("OrderProducts").child(Long.toString(trimLongDate));
        deletePathDatabaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(userID);

        fromPathDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(int i = checkOutID.size()-1; i>= 0; i--){
                        if(dataSnapshot.getKey().equals(checkOutID.get(i))){
                            String itemID = dataSnapshot.getKey();
                            CartProducts cartProducts = dataSnapshot.getValue(CartProducts.class);
                            toPathDatabaseReference.child(itemID).setValue(cartProducts);
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        deletePathDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(int i = checkOutID.size()-1; i>= 0; i--){
                        if(dataSnapshot.getKey().equals(checkOutID.get(i))) {
                            String itemID = dataSnapshot.getKey();
                            deletePathDatabaseReference.child(itemID).removeValue();
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateQuantity(){

        updateQuantityDatabaseReference = FirebaseDatabase.getInstance().getReference("FinishedProducts");
        int listSize = checkOutID.size()-1;
        for(int i = listSize;i>=0;i--){
            String myID = checkOutID.get(i);
            Long orderedQuantity = Long.parseLong(checkOutAmount.get(i));



            Query userQuery = updateQuantityDatabaseReference.orderByChild("itemno").equalTo(Integer.parseInt(myID));
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                        myKey = dataSnapshot.getKey();
                        Products products = dataSnapshot.getValue(Products.class);
                        Long stockQuantity = products.getQuantity();
                        Long finalQuantity = stockQuantity - orderedQuantity;
                        updateQuantityDatabaseReference.child(myKey).child("quantity").setValue(finalQuantity);











                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }





    }

    /*
    public void copyFirebaseData() {
    DatabaseReference questionNodes = FirebaseDatabase.getInstance().getReference().child("questions");
    final DatabaseReference toUsersQuestions = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("questions");

    questionNodes.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot questionCode : dataSnapshot.getChildren()) {
                String questionCodeKey = questionCode.getKey();
                String correctAnswerString = questionCode.child("correctAnswer").getValue(String.class);
                String imageUrlString = questionCode.child("imageUrl").getValue(String.class);
                toUsersQuestions.child(questionCodeKey).child("imageUrl").setValue(imageUrlString);
                toUsersQuestions.child(questionCodeKey).child("correctAnswer").setValue(correctAnswerString);

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}



    private void copyRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                toPath.setValue(dataSnapshot.getValue().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Success!");
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

     */

}