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

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> list;

    public StarAdapter(Context context, ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.star_products,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarAdapter.MyViewHolder holder, int position) {

        Products products = list.get(position);
        holder.name.setText(products.getName());
        holder.price.setText("\u20B1"+Long.toString(products.getPrice())+"/piece");
        Glide.with(context).load(products.getImageURL()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.starProductImg);
            name = itemView.findViewById(R.id.starProductName);
            price = itemView.findViewById(R.id.starProductPrice);
        }
    }
}
