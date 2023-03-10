package com.example.sweetbizmobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.AllOrdersViewholder> {

    DatabaseReference myDatabaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;
    Long count;


    Context context;
    ArrayList<Orders> list;

    public AllOrdersAdapter(Context context, ArrayList<Orders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllOrdersAdapter.AllOrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_orders_products,parent,false);

        return new AllOrdersViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrdersAdapter.AllOrdersViewholder holder, int position) {

        DateFormat dateFormat;
        String stringDate;


        Orders orders = list.get(position);
        holder.allOrdersTotalPrice.setText("\u20B1"+Integer.toString(orders.getRevenue()));
        holder.allOrderNumber.setText("Order No.: "+Long.toString(orders.getOrderno()));
        dateFormat = new SimpleDateFormat("d-MMM-yyyy h:mm a");
        stringDate = dateFormat.format(orders.getDate() * 1000);
        holder.allOrderDate.setText(stringDate);
        holder.status.setText(orders.getStatus().toUpperCase(Locale.ROOT));





        if(holder.status.getText().toString().equals("PENDING")){
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        }else if(holder.status.getText().toString().equals("PAID")){
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.lightGreen));
        }else if(holder.status.getText().toString().equals("CANCELLED")){
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.lightRed));
        }else{
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        holder.linearLayout.removeAllViews();




        Log.d("OrderNumber", holder.allOrderNumber.getText().toString());

        String dbRef = Long.toString(orders.getOrderno());
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("OrderProducts").child(dbRef);

        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 count = snapshot.getChildrenCount();


                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        CartProducts cartProducts = dataSnapshot.getValue(CartProducts.class);



                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View myView = layoutInflater.inflate(R.layout.all_orders_items, null);
                TextView title = myView.findViewById(R.id.allOrdersProductName);
                TextView price = myView.findViewById(R.id.allOrdersProductPrice);
                TextView amount = myView.findViewById(R.id.allOrdersProductAmount);
                TextView description = myView.findViewById(R.id.allOrdersProductDescription);
                ImageView imageView = myView.findViewById(R.id.allOrdersProductImage);
                title.setText(cartProducts.getName());
                price.setText("\u20B1"+Long.toString(cartProducts.getPrice())+"/piece");
                amount.setText(Integer.toString(cartProducts.getAmount()));
                description.setText(cartProducts.getDescription());
                        Glide.with(context.getApplicationContext()).load(cartProducts.getImageURL()).into(imageView);
                holder.linearLayout.addView(myView);



                }


                holder.amount.setText("("+Long.toString(count)+" item(s))");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







/*


        for(int i = 3; i>= 0; i--) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = layoutInflater.inflate(R.layout.all_orders_items, null);
            TextView title = myView.findViewById(R.id.allOrdersProductName);
            TextView price = myView.findViewById(R.id.allOrdersProductPrice);
            TextView amount = myView.findViewById(R.id.allOrdersProductAmount);
            ImageView imageView = myView.findViewById(R.id.allOrdersProductImage);
            title.setText("HERE");
            price.setText("1000");
            amount.setText("10");
            holder.linearLayout.addView(myView);
        }



        TextView tv = new TextView(context);
        tv.setText("hallo hallo");
        holder.linearLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        holder.linearLayout.addView(tv);

 */


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllOrdersViewholder extends RecyclerView.ViewHolder {

        TextView allOrdersTotalPrice, name, price, amount, status, allOrderNumber, allOrderDate;
        LinearLayout linearLayout;


        public AllOrdersViewholder(@NonNull View itemView) {
            super(itemView);

            allOrdersTotalPrice = itemView.findViewById(R.id.allOrdersTotalPrice);
            price = itemView.findViewById(R.id.allOrdersTotalPrice);
            allOrderDate = itemView.findViewById(R.id.allOrdersOrderDate);
            amount = itemView.findViewById(R.id.allOrdersTotalItems);
            status = itemView.findViewById(R.id.totalItemsStatus);
            allOrderNumber = itemView.findViewById(R.id.allOrdersOrderNumber);
            linearLayout = itemView.findViewById(R.id.myLinearLayout);



        }
    }
}
