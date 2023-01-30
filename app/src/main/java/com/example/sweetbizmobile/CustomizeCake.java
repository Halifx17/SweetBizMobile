package com.example.sweetbizmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class CustomizeCake extends AppCompatActivity {

    ImageView customizeCakeImage;
    String [] cakeSizeArray = {"8x3","8x5"};
    String [] icingArray = {"None","Butter Cream","Whipped Cream","Royal Icing"};
    String [] decorationsArray = {"None","Chocolates","Marshmallows","Sprinkles"};
    AutoCompleteTextView autoCompleteIcing, autoCompleteDecorations, autoCompleteCakeSize;
    ArrayAdapter<String> adapterIcing;
    ArrayAdapter<String> adapterDecorations;
    ArrayAdapter<String> adapterCakeSize;
    TextInputLayout textInputLayoutIcing, textInputLayoutDecorations;
    String icing = "None";
    String decorations = "None";
    String cakeSize = "8x3";

    ExtendedFloatingActionButton customizeCakeCheckOutBtn, customizeCakeReserveBtn;

    String imageUrl, name, category, description, price, quantity, totalQuantity, totalPrice;
    Long intPrice, intTotalPrice, intTotalQuantity;
    int intQuantity, totalIcingCost, totalDecorationsCost, totalCakeSizeCost, cakeSizeCost = 500, icingCost = 0, decorationsCost = 0;

    TextView customizeCakeName, customizeCakePrice, customizeCakeTotalPrice, customizeCakeQuantity, customizeIcingPrice, customizeDecorationsPrice, customizeCakeSizePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_cake);

        Intent intent = getIntent();
/*
        intent.putExtra("cakeName",name);
        intent.putExtra("cakeCategory",category);
        intent.putExtra("cakeImageUrl",imageUrl);
        intent.putExtra("cakeDescription",description);
        intent.putExtra("cakePrice",price);
        intent.putExtra("cakeQuantity",quantity);
        intent.putExtra("cakeTotalQuantity",totalQuantity);
        intent.putExtra("cakeTotalPrice",totalPrice);

*/
        intQuantity = intent.getIntExtra("cakeQuantity",0);
        intTotalPrice = intent.getLongExtra("cakeTotalPrice",0);
        intPrice = intent.getLongExtra("cakePrice",0);

        name = intent.getStringExtra("cakeName");
        imageUrl = intent.getStringExtra("cakeImageUrl");
        price = String.valueOf(intent.getLongExtra("cakePrice",0));
        quantity = String.valueOf(intent.getIntExtra("cakeQuantity",0));
        totalPrice = String.valueOf(intent.getLongExtra("cakeTotalPrice",0));
        description = intent.getStringExtra("description");
        category = intent.getStringExtra("category");
        totalQuantity = String.valueOf(intent.getIntExtra("totalQuantity",0));




        customizeCakeImage = findViewById(R.id.customizeCakeImage);
        customizeCakeName = findViewById(R.id.customizeCakeName);
     //   customizeCakePrice = findViewById(R.id.customizeCakePrice);
        customizeCakeTotalPrice = findViewById(R.id.customizeCakeTotalPrice);
        customizeCakeQuantity = findViewById(R.id.customizeCakeQuantity);
        customizeIcingPrice = findViewById(R.id.customizeIcingPrice);
        customizeDecorationsPrice = findViewById(R.id.customizeDecorationsPrice);
        customizeCakeSizePrice = findViewById(R.id.customizeCakeSizePrice);
        customizeCakeCheckOutBtn = findViewById(R.id.checkOutCustomizeCakeBtn);
        customizeCakeReserveBtn = findViewById(R.id.customizeCakeReserveBtn);


        Glide.with(this).load(imageUrl).into(customizeCakeImage);
        customizeCakeTotalPrice.setText(totalPrice);
        customizeCakeName.setText(name);
        customizeCakeSizePrice.setText(price);
        customizeCakeQuantity.setText("x "+quantity);



        autoCompleteIcing = findViewById(R.id.autoCompleteIcing);
        autoCompleteDecorations = findViewById(R.id.autoCompleteDecorations);
        autoCompleteCakeSize = findViewById(R.id.autoCompleteCakeSize);

        adapterCakeSize = new ArrayAdapter<String>(this,R.layout.list_item,cakeSizeArray);
        int itemPosition = adapterCakeSize.getPosition("8x3");
        autoCompleteCakeSize.setText(adapterCakeSize.getItem(itemPosition));
        autoCompleteCakeSize.setAdapter(adapterCakeSize);


        adapterIcing = new ArrayAdapter<String>(this,R.layout.list_item,icingArray);
        /*
        int itemPosition = adapterIcing.getPosition("Royal Icing");
        autoCompleteIcing.setText(adapterIcing.getItem(itemPosition));
         */
        autoCompleteIcing.setAdapter(adapterIcing);

        adapterDecorations = new ArrayAdapter<String>(this,R.layout.list_item,decorationsArray);
        autoCompleteDecorations.setAdapter(adapterDecorations);


        customizeCakeCheckOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CustomizeCake.this,CustomizeCakeCheckOut.class);


                //String//
                intent.putExtra("customizeCakeName",name);
                intent.putExtra("customizeCakeDescription",description);
               // intent.putExtra("customizeCakePrice",price);
                intent.putExtra("customizeCakeImageUrl",imageUrl);

                intent.putExtra("customizeCakeCategory",category);
                intent.putExtra("customizeCakeQuantity",quantity);
                intent.putExtra("customizeCakeTotalQuantity",totalQuantity);
                intent.putExtra("customizeCakeTotalPrice",customizeCakeTotalPrice.getText().toString());
                intent.putExtra("customizeCakeSize",cakeSize);
                intent.putExtra("customizeCakeIcing",icing);
                intent.putExtra("customizeCakeDecorations",decorations);

                //Integer//
                intent.putExtra("customizeCakeSizeCost",cakeSizeCost);
                intent.putExtra("customizeCakeIcingCost",icingCost);
                intent.putExtra("customizeCakeDecorationsCost",decorationsCost);





                startActivity(intent);


            }
        });

        customizeCakeReserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        autoCompleteCakeSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




                String currentTotalPrice = customizeCakeTotalPrice.getText().toString();
                String currentCakeSizePrice = customizeCakeSizePrice.getText().toString();



                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentCakeSizePrice = Integer.parseInt(currentCakeSizePrice) * intQuantity;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentCakeSizePrice;


                totalCakeSizeCost = 0;


                cakeSize = adapterView.getItemAtPosition(i).toString();

                if(cakeSize.equals("8x3")){
                    cakeSizeCost = Math.toIntExact(intPrice);
                }else if(cakeSize.equals("8x5")){
                    cakeSizeCost = Math.toIntExact(intPrice)+120;
                }

                totalCakeSizeCost = intQuantity * cakeSizeCost;
                finalTotalPrice = finalTotalPrice + totalCakeSizeCost;

                customizeCakeSizePrice.setText(Integer.toString(cakeSizeCost));
                customizeCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });




        autoCompleteIcing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




                String currentTotalPrice = customizeCakeTotalPrice.getText().toString();
                String currentIcingPrice = customizeIcingPrice.getText().toString();



                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentIcingPrice = Integer.parseInt(currentIcingPrice) * intQuantity;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentIcingPrice;


                totalIcingCost = 0;

                icing = adapterView.getItemAtPosition(i).toString();
                if(icing.equals("None")){
                    icingCost = 0;
                }else if(icing.equals("Butter Cream")){
                    icingCost = 10;
                }else if(icing.equals("Whipped Cream")){
                    icingCost = 20;
                }else if(icing.equals("Royal Icing")){
                    icingCost = 30;
                }else{
                    icingCost = 0;
                }

                totalIcingCost = intQuantity * icingCost;
                finalTotalPrice = finalTotalPrice + totalIcingCost;

                customizeIcingPrice.setText(Integer.toString(icingCost));
                customizeCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });

        autoCompleteDecorations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String currentTotalPrice = customizeCakeTotalPrice.getText().toString();
                String currentDecorationsPrice = customizeDecorationsPrice.getText().toString();

                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentDecorationsPrice = Integer.parseInt(currentDecorationsPrice) * intQuantity;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentDecorationsPrice;

                totalDecorationsCost = 0;

                decorations = adapterView.getItemAtPosition(i).toString();
                if(decorations.equals("None")){
                    decorationsCost = 0;
                }else if(decorations.equals("Chocolates")){
                    decorationsCost = 10;
                }else if(decorations.equals("Marshmallows")){
                    decorationsCost = 20;
                }else if(decorations.equals("Sprinkles")){
                    decorationsCost = 30;
                }else{
                    decorationsCost = 0;
                }

                totalDecorationsCost = intQuantity * decorationsCost;
                finalTotalPrice = finalTotalPrice + totalDecorationsCost;

                customizeDecorationsPrice.setText(Integer.toString(decorationsCost));
                customizeCakeTotalPrice.setText(Integer.toString(finalTotalPrice));
            }
        });




    }
}