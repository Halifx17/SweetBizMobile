package com.example.sweetbizmobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllOrdersViewholder extends RecyclerView.ViewHolder {

        TextView allOrdersTotalPrice, name, price, amount;

        public AllOrdersViewholder(@NonNull View itemView) {
            super(itemView);

            allOrdersTotalPrice = itemView.findViewById(R.id.allOrdersTotalPrice);
            name = itemView.findViewById(R.id.allOrdersProductName);
            price = itemView.findViewById(R.id.allOrdersTotalPrice);
            amount = itemView.findViewById(R.id.allOrdersProductAmount);


        }
    }
}
