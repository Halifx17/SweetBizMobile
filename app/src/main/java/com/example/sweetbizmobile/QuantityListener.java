package com.example.sweetbizmobile;

import android.widget.CheckBox;

import java.util.ArrayList;

public interface QuantityListener {
    
    void onQuantityChange(ArrayList<String> arrayList);
    void onCheckBoxChange(ArrayList<CheckBox> arrayList);
    void onTotalPriceChange(ArrayList<String> arrayList);
    void onNameChange(ArrayList<String> arrayList);
    void onAmountChange(ArrayList<String> arrayList);
    
    
}


