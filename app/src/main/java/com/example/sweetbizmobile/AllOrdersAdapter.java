package com.example.sweetbizmobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.AllOrdersViewholder> {

    DatabaseReference myDatabaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;
    int numberOfItems;
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



        Orders orders = list.get(position);
        holder.allOrdersTotalPrice.setText(Integer.toString(orders.getRevenue()));
        holder.orderNumber.setText(Long.toString(orders.getOrderno()));

        Log.d("OrderNumber", holder.orderNumber.getText().toString());

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("OrderProducts").child(holder.orderNumber.getText().toString());

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
                ImageView imageView = myView.findViewById(R.id.allOrdersProductImage);
                title.setText(cartProducts.getName());
                price.setText("\u20B1"+Long.toString(cartProducts.getPrice())+"/piece");
                amount.setText(Integer.toString(cartProducts.getAmount()));
                        Glide.with(context).load(cartProducts.getImageURL()).into(imageView);
                holder.linearLayout.addView(myView);

                numberOfItems++;

                }



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

        TextView allOrdersTotalPrice, name, price, amount, orderNumber;
        LinearLayout linearLayout;


        public AllOrdersViewholder(@NonNull View itemView) {
            super(itemView);

            allOrdersTotalPrice = itemView.findViewById(R.id.allOrdersTotalPrice);
            price = itemView.findViewById(R.id.allOrdersTotalPrice);
            amount = itemView.findViewById(R.id.allOrdersProductAmount);
            orderNumber = itemView.findViewById(R.id.allOrdersOrderNumber);
            linearLayout = itemView.findViewById(R.id.myLinearLayout);



        }
    }
}
