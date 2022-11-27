package com.example.sweetbizmobile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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



    boolean isSelectedAll;

    Context context;
    ArrayList<CartProducts> list;
    QuantityListener quantityListener;
    ArrayList<String> arrayList0 = new ArrayList<>();
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<String> totalPrices = new ArrayList<>();

    public CartAdapter(Context context, ArrayList<CartProducts> list, QuantityListener quantityListener) {
        this.context = context;
        this.list = list;
        this.quantityListener = quantityListener;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_products,parent,false);





        return new CartViewHolder(view);
    }

    public void checkBoxOperation(ArrayList<CheckBox> checkBoxList, boolean what){
        for (CheckBox checkBox : checkBoxList ){
            checkBox.setChecked(what);
        }
    }

    public void selectAll(){

        isSelectedAll=true;
        notifyDataSetChanged();

    }
    public void unselectAll(){



        isSelectedAll=false;
        notifyDataSetChanged();


    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {




        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        CartProducts cartProducts = list.get(position);
        holder.name.setText(cartProducts.getName());
        holder.price.setText("\u20B1"+Long.toString(cartProducts.getPrice())+"/piece");
        holder.amount.setText(Integer.toString(cartProducts.getAmount()));
        holder.quantity.setText(Long.toString(cartProducts.getQuantity()));
        holder.itemNo.setText(Long.toString(cartProducts.getItemno()));
        holder.totalPrice.setText(Long.toString(cartProducts.getTotalPrice()));
        Glide.with(context).load(cartProducts.getImageURL()).into(holder.image);

        /*
        arrayList0.add(holder.itemNo.getText().toString());
        quantityListener.onQuantityChange(arrayList0);
        totalPrices.add(holder.totalPrice.getText().toString());
        quantityListener.onTotalPriceChange(totalPrices);

         */


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                if(holder.checkBox.isChecked()){
                    arrayList0.add(holder.itemNo.getText().toString());
                    totalPrices.add(holder.totalPrice.getText().toString());
                   // Log.d("CheckBox", holder.itemNo.getText().toString());
                }else{
                    arrayList0.remove(holder.itemNo.getText().toString());
                    totalPrices.remove(holder.totalPrice.getText().toString());

                }
                quantityListener.onQuantityChange(arrayList0);
                quantityListener.onTotalPriceChange(totalPrices);


            }


        });

        checkBoxes.add(holder.checkBox);
        quantityListener.onCheckBoxChange(checkBoxes);




        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Carts")
                        .child(userID)
                        .child(holder.itemNo.getText()
                                .toString());


                int myAmountAdd;

                int myAmount = Integer.parseInt(holder.amount.getText().toString());
                Long myQuantity = Long.parseLong(holder.quantity.getText().toString());
                String myPrice = holder.price.getText().toString();
                String myPriceNumberOnly = myPrice.replaceAll("[^0-9.]","");
                Long myPriceLong = Long.parseLong(myPriceNumberOnly);


                if(myAmount <= myQuantity){

                    myAmountAdd = myAmount + 1;

                    databaseReference.child("amount").setValue(myAmountAdd);
                    databaseReference.child("totalPrice").setValue(myAmountAdd*myPriceLong);
                    Intent intent = new Intent(context,Home.class);
                    String fragment = "cartFragmentRefresh";
                    intent.putExtra("fragment",fragment);
                    context.startActivity(intent);

                    Log.d("MessageAdd", String.valueOf(myAmountAdd));

                }else{
                    Toast.makeText(context,holder.quantity.getText().toString()+" is the maximum amount",Toast.LENGTH_SHORT).show();
                }

                arrayList0.clear();
                totalPrices.clear();




            }

        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Carts")
                        .child(userID)
                        .child(holder.itemNo.getText()
                                .toString());


                int myAmountMinus;

                int myAmount = Integer.parseInt(holder.amount.getText().toString());
                Long myQuantity = Long.parseLong(holder.quantity.getText().toString());
                String myPrice = holder.price.getText().toString();
                String myPriceNumberOnly = myPrice.replaceAll("[^0-9.]","");
                Long myPriceLong = Long.parseLong(myPriceNumberOnly);



                if(myAmount != 0){
                    myAmountMinus = myAmount - 1;
                    databaseReference.child("amount").setValue(myAmountMinus);
                    databaseReference.child("totalPrice").setValue(myAmountMinus*myPriceLong);
                    Intent intent = new Intent(context,Home.class);
                    String fragment = "cartFragmentRefresh";
                    intent.putExtra("fragment",fragment);
                    context.startActivity(intent);
                    Log.d("MessageMinus", String.valueOf(myAmountMinus));
                }else{
                    Toast.makeText(context,"Cannot go below zero",Toast.LENGTH_SHORT).show();
                }


            }
        });








        String myAmount = holder.amount.getText().toString();
        String myPrice = holder.price.getText().toString();
        String myPriceNumberOnly= myPrice.replaceAll("[^0-9.]","");

       // Long myTotalPrice = Integer.parseInt(myAmount) * Long.parseLong(myPriceNumberOnly);
       // databaseReference.child("totalPrice").setValue(myTotalPrice);

      //  holder.totalPrice.setText(Long.toString(myTotalPrice));

       // databaseReference.child("amount").setValue(Integer.parseInt(myAmount));





        Log.d("TAG",holder.totalPrice.getText().toString());
     //   Log.d("UID",userID);
      //  Log.d("Amount",myAmount);





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{


        TextView name, price, amount, quantity, itemNo, totalPrice;
        ImageView image;
        CheckBox checkBox;
        Button addBtn, minusBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);


            checkBox = itemView.findViewById(R.id.cartProductCheckBox);
            name = itemView.findViewById(R.id.cartProductName);
            price = itemView.findViewById(R.id.cartProductPrice);
            image = itemView.findViewById(R.id.cartProductImage);
            amount = itemView.findViewById(R.id.cartProductAmount);
            quantity = itemView.findViewById(R.id.cartProductQuantity);
            quantity.setVisibility(View.INVISIBLE);
            itemNo = itemView.findViewById(R.id.cartProductItemNumber);
            itemNo.setVisibility(View.INVISIBLE);
            totalPrice = itemView.findViewById(R.id.cartProductTotalPrice);
            totalPrice.setVisibility(View.INVISIBLE);
            addBtn = itemView.findViewById(R.id.addBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);



/*
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(checkBox.isChecked()){
                        arrayList0.add(itemNo.getText().toString());
                        Log.d("CheckBox", itemNo.getText().toString());
                    }else{
                        arrayList0.remove(itemNo.getText().toString());
                    }
                    quantityListener.onQuantityChange(arrayList0);
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







        }


    }
}
