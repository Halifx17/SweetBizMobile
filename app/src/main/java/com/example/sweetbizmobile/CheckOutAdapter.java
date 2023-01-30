package com.example.sweetbizmobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder> {

    Context context;
    ArrayList <CartProducts> list;


    public CheckOutAdapter(Context context, ArrayList<CartProducts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CheckOutAdapter.CheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_cart,parent,false);

        return new CheckOutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutAdapter.CheckOutViewHolder holder, int position) {

        CartProducts cartProducts = list.get(position);
        holder.name.setText(cartProducts.getName());
        holder.description.setText(cartProducts.getDescription());
        holder.price.setText("\u20B1"+Long.toString(cartProducts.getPrice())+"/piece");
        holder.amount.setText(Integer.toString(cartProducts.getAmount()));
        holder.subtotal.setText("\u20B1"+Long.toString(cartProducts.getTotalPrice()));
        Glide.with(context).load(cartProducts.getImageURL()).into(holder.image);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CheckOutViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, amount, quantity, itemNo, totalPrice, description, subtotal;
        ImageView image;

        public CheckOutViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.checkOutProductName);
            description = itemView.findViewById(R.id.checkOutProductDescription);
            price = itemView.findViewById(R.id.checkOutProductPrice);
            amount = itemView.findViewById(R.id.checkOutProductAmount);
            image = itemView.findViewById(R.id.checkOutProductImage);
            subtotal = itemView.findViewById(R.id.checkOutProductSubtotal);


        }
    }
}
