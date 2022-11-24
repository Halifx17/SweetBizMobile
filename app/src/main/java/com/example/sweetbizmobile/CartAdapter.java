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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartProducts> list;

    public CartAdapter(Context context, ArrayList<CartProducts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_products,parent,false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {

        CartProducts cartProducts = list.get(position);
        holder.name.setText(cartProducts.getName());
        holder.price.setText("\u20B1"+Long.toString(cartProducts.getPrice())+"/piece");
        Glide.with(context).load(cartProducts.getImageURL()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView name, price;
        ImageView image;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cartProductName);
            price = itemView.findViewById(R.id.cartProductPrice);
            image = itemView.findViewById(R.id.cartProductImage);

        }
    }
}
