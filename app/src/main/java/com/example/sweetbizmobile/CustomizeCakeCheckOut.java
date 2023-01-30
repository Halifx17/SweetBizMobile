package com.example.sweetbizmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class CustomizeCakeCheckOut extends AppCompatActivity {

    TextView cakeName, cakeSize, cakeIcing, cakeDecorations, cakeQuantity,
            customizedCakeCheckOutTotalPrice,
            cakeTotalPrice,
            cakeSubTotal,
            cakeIcingCost,
            cakeSizeCost,
            cakeDecorationsCost;
    ImageView cakeImage;

    ExtendedFloatingActionButton termsAndConditionsBtn, placeOrderBtn;
    CheckBox checkBoxTermsAndConditions;

    String name, imageUrl, size, icing, decorations, quantity;
    int icingCost, sizeCost, decorationsCost, totalPrice, intQuantity, subTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_cake_check_out);

        Intent intent = getIntent();

        name = intent.getStringExtra("customizeCakeName");
        imageUrl = intent.getStringExtra("customizeCakeImageUrl");
        size = intent.getStringExtra("customizeCakeSize");
        icing = intent.getStringExtra("customizeCakeIcing");
        decorations = intent.getStringExtra("customizeCakeDecorations");
        quantity = intent.getStringExtra("customizeCakeQuantity");

        sizeCost = intent.getIntExtra("customizeCakeSizeCost",0);
        icingCost = intent.getIntExtra("customizeCakeIcingCost",0);
        decorationsCost = intent.getIntExtra("customizeCakeDecorationsCost",0);

        customizedCakeCheckOutTotalPrice = findViewById(R.id.customizedCakeCheckOutTotalPrice);
        cakeTotalPrice = findViewById(R.id.cccoTotalPrice);
        cakeSubTotal = findViewById(R.id.cccoSubTotal);

        cakeName = findViewById(R.id.cccoCakeName);
        cakeSize = findViewById(R.id.cccoCakeSize);
        cakeIcing = findViewById(R.id.cccoCakeIcing);
        cakeDecorations = findViewById(R.id.cccoCakeDecorations);
        cakeQuantity = findViewById(R.id.cccoQuantity);
        cakeImage = findViewById(R.id.cccoImage);

        cakeSizeCost = findViewById(R.id.cccoCakeSizePrice);
        cakeIcingCost = findViewById(R.id.cccoCakeIcingPrice);
        cakeDecorationsCost = findViewById(R.id.cccoCakeDecorationsPrice);

        cakeSizeCost.setText(Integer.toString(sizeCost));
        cakeIcingCost.setText(Integer.toString(icingCost));
        cakeDecorationsCost.setText(Integer.toString(decorationsCost));

        cakeName.setText(name);
        cakeSize.setText(size);
        cakeIcing.setText(icing);
        cakeDecorations.setText(decorations);
        cakeQuantity.setText(quantity);
        Glide.with(this).load(imageUrl).into(cakeImage);

        intQuantity = Integer.parseInt(quantity);
        subTotal = (sizeCost + icingCost + decorationsCost);
        totalPrice = (sizeCost + icingCost + decorationsCost)*intQuantity;

        cakeSubTotal.setText(Integer.toString(subTotal));
        customizedCakeCheckOutTotalPrice.setText(Integer.toString(totalPrice));
        cakeTotalPrice.setText(Integer.toString(totalPrice));

        termsAndConditionsBtn = findViewById(R.id.cccotermsAndConditions);
        placeOrderBtn = findViewById(R.id.cccoPlaceOrderBtn);
        checkBoxTermsAndConditions = findViewById(R.id.cccoCheckBoxTermsAndConditions);

        String text = "Please read and accept our TERMS AND CONDITIONS before proceeding";
        Spannable centeredText = new SpannableString(text);
        centeredText.setSpan(
                new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, text.length() - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
        );

        termsAndConditionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(CustomizeCakeCheckOut.this);

                builder.setTitle("SweetBiz Terms and Conditions");
                builder.setMessage("1. By accepting. . . . \n2. Orders will be cancelled after. . . .\n 3. All orders will be acquired through pickup. . . .");
                builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkBoxTermsAndConditions.setChecked(true);
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkBoxTermsAndConditions.setChecked(false);

                    }
                });

                builder.show();
            }
        });


        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBoxTermsAndConditions.isChecked()){


                    //  placeOrder();
                //    copyRecord();
                //    updateQuantity();

                    Intent intent = new Intent(getApplicationContext(),OrderPlaced.class);

                    Toast.makeText(getApplicationContext(),"centeredText",Toast.LENGTH_LONG).show();

                    //  startActivity(intent);


                }else{
                    Toast.makeText(getApplicationContext(),centeredText,Toast.LENGTH_LONG).show();
                }


            }
        });






    }


}