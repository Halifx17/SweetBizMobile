package com.example.sweetbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AllOrdersInnerAdapter extends RecyclerView.Adapter<AllOrdersInnerAdapter.Viewholder> {


    Context context;
    ArrayList<CartProducts> list;

    public AllOrdersInnerAdapter(Context context, ArrayList<CartProducts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_orders_inner_products,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        CartProducts cartProducts = list.get(position);
        holder.name.setText(cartProducts.getName());
        holder.price.setText("\u20B1"+Long.toString(cartProducts.getPrice())+"/piece");
        holder.amount.setText(Integer.toString(cartProducts.getAmount()));
        Glide.with(context).load(cartProducts.getImageURL()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView name, price, amount;
        ImageView image;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.allOrdersInnerProductName);
            price = itemView.findViewById(R.id.allOrdersInnerProductPrice);
            amount = itemView.findViewById(R.id.allOrdersInnerProductAmount);
            image = itemView.findViewById(R.id.allOrdersInnerProductImage);
        }
    }
}
