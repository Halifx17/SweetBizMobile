package com.example.sweetbizmobile;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.AllOrdersViewholder> {

    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;


    Activity activity;
    Context context;
    ArrayList<Orders> list;
    ArrayList<CartProducts> list2;

    public AllOrdersAdapter(Context context, ArrayList<Orders> list, ArrayList<CartProducts> list2) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
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

        AllOrdersInnerAdapter allOrdersInnerAdapter = new AllOrdersInnerAdapter(context,list2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.innerRecyclerAllOrders.setLayoutManager(linearLayoutManager);
        holder.innerRecyclerAllOrders.setAdapter(allOrdersInnerAdapter);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllOrdersViewholder extends RecyclerView.ViewHolder {

        TextView allOrdersTotalPrice, name, price, amount;
        RecyclerView innerRecyclerAllOrders;

        public AllOrdersViewholder(@NonNull View itemView) {
            super(itemView);

            allOrdersTotalPrice = itemView.findViewById(R.id.allOrdersTotalPrice);
            price = itemView.findViewById(R.id.allOrdersTotalPrice);
            innerRecyclerAllOrders = itemView.findViewById(R.id.innerRecyclerAllOrders);


        }
    }
}
