package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Pizza extends AppCompatActivity {

    ProgressBar progressBar;
    EditText searchEt;
    ImageButton sortBtn;

    RecyclerView recyclerView;
    ArrayList<Products> list;
    DatabaseReference databaseReference;
    StarAdapter starAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutPizza);
        searchEt = findViewById(R.id.searchEtPizza);
        progressBar = findViewById(R.id.progressBarPizza);
        sortBtn = findViewById(R.id.sortBtnPizza);
        recyclerView = findViewById(R.id.recyclerPizza);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        starAdapter = new StarAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(starAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("FinishedProducts");

        loadStoreData();


        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {
                    starAdapter.getFilter().filter(s);

                }
                catch (Exception e){
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), sortBtn);
        popupMenu.getMenu().add(Menu.NONE,0,0,"Ascending");
        popupMenu.getMenu().add(Menu.NONE,1,1,"Descending");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == 0){
                    Collections.sort(list, new SortProductsAsc());
                    starAdapter.notifyDataSetChanged();
                }
                else if (id == 1){
                    Collections.sort(list, new SortProductsDesc());
                    starAdapter.notifyDataSetChanged();
                }

                return false;
            }
        });


        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupMenu.show();

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                loadStoreData();

            }
        });
    }

    private void loadStoreData() {
        progressBar.setVisibility(View.VISIBLE);

        list.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){


                    Products products = dataSnapshot.getValue(Products.class);
                    if(products.getCategory().equals("Pizza")){
                        list.add(products);
                    }


                }
                starAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressBar.setVisibility(View.GONE);

    }


    public class SortProductsAsc implements Comparator<Products> {

        @Override
        public int compare(Products left, Products right) {
            return left.getName().compareTo(right.getName());
        }
    }

    public class SortProductsDesc implements Comparator<Products>{

        @Override
        public int compare(Products left, Products right) {
            return right.getName().compareTo(left.getName());
        }
    }
}