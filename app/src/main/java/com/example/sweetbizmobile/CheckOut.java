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

import java.util.ArrayList;

public class CheckOut extends AppCompatActivity{

    RecyclerView recyclerView;
    ArrayList<CartProducts> list;
    DatabaseReference databaseReference, fromPathDatabaseReference, toPathDatabaseReference, deletePathDatabaseReference;
    CheckOutAdapter checkOutAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    FirebaseUser user;
    FirebaseAuth mAuth;

    String userID;

    ExtendedFloatingActionButton placeOrderBtn;

    String stringCheckOutID, stringCheckOutPrice;
    TextView checkOutTotalPrice;

    ArrayList<String> checkOutID = new ArrayList<>();
    ArrayList<String> checkOutPrice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Bundle bundle = getIntent().getExtras();
        checkOutID = bundle.getStringArrayList("checkOutID");
        checkOutPrice = bundle.getStringArrayList("checkOutPrice");
        Log.d("MyArrayID", checkOutID.toString());
        Log.d("MyArrayPrice", checkOutPrice.toString());

        checkOutTotalPrice = findViewById(R.id.checkOutProductTotalPrice);


        placeOrderBtn = findViewById(R.id.placeOrderBtn);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        userID = user.getUid();

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

        int finalTotalSumOfPrice = totalSumOfPrice;

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                copyRecord();






            }
        });



    }

    private void copyRecord() {

        fromPathDatabaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(userID);
        toPathDatabaseReference = FirebaseDatabase.getInstance().getReference("OrderProducts").child(userID);
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