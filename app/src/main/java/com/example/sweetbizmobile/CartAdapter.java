package com.example.sweetbizmobile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    DatabaseReference databaseReference, cartDatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;

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
        holder.amount.setText(Integer.toString(cartProducts.getAmount()));
        holder.quantity.setText(Long.toString(cartProducts.getQuantity()));
        holder.itemNo.setText(Long.toString(cartProducts.getItemno()));
        Glide.with(context).load(cartProducts.getImageURL()).into(holder.image);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView name, price, amount, quantity, itemNo;
        ImageView image;
        CheckBox checkBox;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuth = FirebaseAuth.getInstance();

            user = mAuth.getCurrentUser();
            userID = user.getUid();

            checkBox = itemView.findViewById(R.id.cartProductCheckBox);
            name = itemView.findViewById(R.id.cartProductName);
            price = itemView.findViewById(R.id.cartProductPrice);
            image = itemView.findViewById(R.id.cartProductImage);
            amount = itemView.findViewById(R.id.cartProductAmount);
            quantity = itemView.findViewById(R.id.cartProductQuantity);
            quantity.setVisibility(View.INVISIBLE);
            itemNo = itemView.findViewById(R.id.cartProductItemNumber);
            itemNo.setVisibility(View.INVISIBLE);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(checkBox.isChecked()){
                        Log.d("CheckBox", itemNo.getText().toString());
                    }
                }
            });

/*

            itemView.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String itemNoID = itemNo.getText().toString();
                    cartDatabase = FirebaseDatabase.getInstance().getReference("Carts").child(userID).child(itemNoID);
                    int position = getBindingAdapterPosition();
                    list.remove(position);
                    cartDatabase.removeValue();

                }
            });

 */




            itemView.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int myAmount = Integer.parseInt(amount.getText().toString());
                    Long myQuantity = Long.parseLong(quantity.getText().toString());

                    if(myAmount <= myQuantity){
                        int myAmountAdd = myAmount + 1;
                        amount.setText(String.valueOf(myAmountAdd));
                        Log.d("MessageAdd", String.valueOf(myAmountAdd));
                    }else{
                        Toast.makeText(context,quantity.getText().toString()+" is the maximum amount",Toast.LENGTH_SHORT).show();
                    }



                }
            });

            itemView.findViewById(R.id.minusBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int myAmount = Integer.parseInt(amount.getText().toString());
                    Long myQuantity = Long.parseLong(quantity.getText().toString());

                    if(myAmount != 0){
                        int myAmountMinus = myAmount - 1;
                        amount.setText(String.valueOf(myAmountMinus));
                        Log.d("MessageMinus", String.valueOf(myAmountMinus));
                    }else{
                        Toast.makeText(context,"Cannot go below zero",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }
}
