package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomCakes extends AppCompatActivity {

    String [] cakeSizeArray = {"8x3","8x5"};
    String [] cakeCakeArray = {"None","Chiffon Cake","Pound Cake","Sponge Cake"};
    String [] cakeSyrupArray = {"None","VANILLA SYRUP","HONEY SYRUP","CHOCOLATE SYRUP"};
    String [] cakeFillingArray = {"None","Cherry Filling","Chocolate Filling","Vanilla Filling"};
    String [] icingArray = {"None","Butter Cream","Whipped Cream","Royal Icing"};
    String [] decorationsArray = {"None","Chocolates","Marshmallows","Sprinkles"};

    AutoCompleteTextView
            autoCompleteCakeSize,
            autoCompleteCakeCake,
            autoCompleteCakeSyrup,
            autoCompleteCakeFilling,
            autoCompleteIcing,
            autoCompleteDecorations;

    ArrayAdapter<String> adapterCakeSize;
    ArrayAdapter<String> adapterCakeCake;
    ArrayAdapter<String> adapterCakeSyrup;
    ArrayAdapter<String> adapterCakeFilling;
    ArrayAdapter<String> adapterIcing;
    ArrayAdapter<String> adapterDecorations;

    TextInputLayout textInputLayoutIcing, textInputLayoutDecorations;

    String cakeSize = "8x3";
    String cake = "None";
    String syrup = "None";
    String filling = "None";
    String coating = "None";
    String icing = "None";
    String decorations = "None";


    ExtendedFloatingActionButton customCakeCheckOutBtn, customCakeReserveBtn;

    String imageUrl, name, category, description, price, quantity, totalQuantity, totalPrice;
    Long intPrice, intTotalPrice, intTotalQuantity;

    int intQuantity, minAmount = 1,
            totalIcingCost,
            totalDecorationsCost,
            totalCakeSizeCost,
            totalCakeSyrupCost,
            totalCakeFillingCost,
            totalCakeCakeCost,
            cakeSizeCost = 800,
            cakeCakeCost = 0,
            syrupCost = 0,
            fillingCost = 0,
            icingCost = 0,
            decorationsCost = 0;


    TextView customCakeName,
            customCakeSizePrice,
            customCakeCakePrice,
            customCakeSyrupPrice,
            customCakeFillingPrice,
            customIcingPrice,
            customDecorationsPrice,
            customCakeTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cakes);


        autoCompleteCakeSize = findViewById(R.id.autoCompleteCustomCakeSize);
        autoCompleteCakeCake = findViewById(R.id.autoCompleteCakeCake);
        autoCompleteCakeSyrup = findViewById(R.id.autoCompleteCakeSyrup);
        autoCompleteCakeFilling = findViewById(R.id.autoCompleteCakeFilling);
        autoCompleteIcing = findViewById(R.id.autoCompleteCakeIcing);
        autoCompleteDecorations = findViewById(R.id.autoCompleteCakeDecorations);





        adapterCakeSize = new ArrayAdapter<String>(this,R.layout.list_item,cakeSizeArray);
        int itemPosition = adapterCakeSize.getPosition("8x3");
        autoCompleteCakeSize.setText(adapterCakeSize.getItem(itemPosition));
        autoCompleteCakeSize.setAdapter(adapterCakeSize);

        adapterCakeCake = new ArrayAdapter<String>(this,R.layout.list_item,cakeCakeArray);
        autoCompleteCakeCake.setAdapter(adapterCakeCake);

        adapterCakeSyrup = new ArrayAdapter<String>(this,R.layout.list_item,cakeSyrupArray);
        autoCompleteCakeSyrup.setAdapter(adapterCakeSyrup);

        adapterCakeFilling = new ArrayAdapter<String>(this,R.layout.list_item,cakeFillingArray);
        autoCompleteCakeFilling.setAdapter(adapterCakeFilling);

        adapterIcing = new ArrayAdapter<String>(this,R.layout.list_item,icingArray);
        autoCompleteIcing.setAdapter(adapterIcing);

        adapterDecorations = new ArrayAdapter<String>(this,R.layout.list_item,decorationsArray);
        autoCompleteDecorations.setAdapter(adapterDecorations);

        customCakeSizePrice = findViewById(R.id.customCakeSizePrice);
        customCakeCakePrice = findViewById(R.id.customCakeCakePrice);
        customCakeSyrupPrice = findViewById(R.id.customCakeSyrupPrice);
        customCakeFillingPrice = findViewById(R.id.customCakeFillingPrice);
        customIcingPrice = findViewById(R.id.customCakeIcingPrice);
        customDecorationsPrice = findViewById(R.id.customCakeDecorationsPrice);
        customCakeTotalPrice = findViewById(R.id.customCakeTotalPrice);


        customCakeSizePrice.setText(Integer.toString(cakeSizeCost));
        customCakeTotalPrice.setText(Integer.toString(cakeSizeCost));






        autoCompleteCakeSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String currentTotalPrice = customCakeTotalPrice.getText().toString();
                String currentCakeSizePrice = customCakeSizePrice.getText().toString();


                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentCakeSizePrice = Integer.parseInt(currentCakeSizePrice) * minAmount;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentCakeSizePrice;




                totalCakeSizeCost = 0;

                cakeSize = adapterView.getItemAtPosition(i).toString();

                if(cakeSize.equals("8x3")){
                    cakeSizeCost = 800;
                }else if(cakeSize.equals("8x5")){
                    cakeSizeCost = 1600;
                }

                totalCakeSizeCost = cakeSizeCost * minAmount;
                finalTotalPrice = finalTotalPrice + totalCakeSizeCost;

                Log.d("TAG", String.valueOf(cakeSizeCost));

                customCakeSizePrice.setText(Integer.toString(cakeSizeCost));
                customCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });

        autoCompleteCakeCake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String currentTotalPrice = customCakeTotalPrice.getText().toString();
                String currentCakeCakePrice = customCakeCakePrice.getText().toString();


                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentCakeCakePrice = Integer.parseInt(currentCakeCakePrice) * minAmount;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentCakeCakePrice;

                totalCakeCakeCost = 0;

                cake = adapterView.getItemAtPosition(i).toString();

                if(cake.equals("None")){
                    cakeCakeCost = 0;
                }else if(cake.equals("Chiffon Cake")){
                    cakeCakeCost = 100;
                }else if(cake.equals("Pound Cake")){
                    cakeCakeCost = 200;
                }else if(cake.equals("Sponge Cake")){
                    cakeCakeCost = 300;
                }

                totalCakeCakeCost = cakeCakeCost * minAmount;
                finalTotalPrice = finalTotalPrice + totalCakeCakeCost;

                customCakeCakePrice.setText(Integer.toString(cakeCakeCost));
                customCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });


        autoCompleteCakeSyrup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String currentTotalPrice = customCakeTotalPrice.getText().toString();
                String currentCakeCakePrice = customCakeSyrupPrice.getText().toString();


                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentCakeSyrupPrice = Integer.parseInt(currentCakeCakePrice) * minAmount;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentCakeSyrupPrice;

                totalCakeSyrupCost = 0;

                syrup = adapterView.getItemAtPosition(i).toString();


                if(syrup.equals("None")){
                    syrupCost = 0;
                }else if(syrup.equals("VANILLA SYRUP")){
                    syrupCost = 100;
                }else if(syrup.equals("HONEY SYRUP")){
                    syrupCost = 200;
                }else if(syrup.equals("CHOCOLATE SYRUP")){
                    syrupCost = 300;
                }

                totalCakeSyrupCost = syrupCost * minAmount;
                finalTotalPrice = finalTotalPrice + totalCakeSyrupCost;

                customCakeSyrupPrice.setText(Integer.toString(syrupCost));
                customCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });

        autoCompleteCakeFilling.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String currentTotalPrice = customCakeTotalPrice.getText().toString();
                String currentCakeFillingPrice = customCakeFillingPrice.getText().toString();


                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentCakeFillingPrice = Integer.parseInt(currentCakeFillingPrice) * minAmount;

                int finalTotalPrice = intCurrentTotalPrice - intCurrentCakeFillingPrice;

                totalCakeFillingCost = 0;

                filling = adapterView.getItemAtPosition(i).toString();


                String [] cakeFillingArray = {"None","Cherry Filling","Chocolate Filling","Vanilla Filling"};

                if(filling.equals("None")){
                    fillingCost = 0;
                }else if(filling.equals("Cherry Filling")){
                    fillingCost = 100;
                }else if(filling.equals("Chocolate Filling")){
                    fillingCost = 200;
                }else if(filling.equals("Vanilla Filling")){
                    fillingCost = 300;
                }

                totalCakeFillingCost = fillingCost * minAmount;
                finalTotalPrice = finalTotalPrice + totalCakeFillingCost;

                customCakeFillingPrice.setText(Integer.toString(fillingCost));
                customCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });


        autoCompleteIcing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




                String currentTotalPrice = customCakeTotalPrice.getText().toString();
                String currentIcingPrice = customIcingPrice.getText().toString();

                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentIcingPrice = Integer.parseInt(currentIcingPrice) * minAmount;

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

                totalIcingCost = minAmount * icingCost;
                finalTotalPrice = finalTotalPrice + totalIcingCost;

                customIcingPrice.setText(Integer.toString(icingCost));
                customCakeTotalPrice.setText(Integer.toString(finalTotalPrice));

            }
        });


        autoCompleteDecorations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String currentTotalPrice = customCakeTotalPrice.getText().toString();
                String currentDecorationsPrice = customDecorationsPrice.getText().toString();



                int intCurrentTotalPrice = Integer.parseInt(currentTotalPrice);
                int intCurrentDecorationsPrice = Integer.parseInt(currentDecorationsPrice) * minAmount;

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

                totalDecorationsCost = minAmount * decorationsCost;
                finalTotalPrice = finalTotalPrice + totalDecorationsCost;

                customDecorationsPrice.setText(Integer.toString(decorationsCost));
                customCakeTotalPrice.setText(Integer.toString(finalTotalPrice));
            }
        });









    }


}