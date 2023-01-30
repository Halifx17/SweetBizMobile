package com.example.sweetbizmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {


    ProgressBar progressBar;
    EditText searchEt;
    ImageButton sortBtn;

    RecyclerView recyclerView;
    ArrayList<Products> list;
    DatabaseReference databaseReference;
    StarAdapter starAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    CardView cakes, drinks, cupcakes, pizza, donuts;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store,container,false);

        cakes = view.findViewById(R.id.cakes);
        drinks = view.findViewById(R.id.drinks);
        cupcakes = view.findViewById(R.id.cupcakes);
        pizza = view.findViewById(R.id.pizza);
        donuts = view.findViewById(R.id.donuts);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        searchEt = view.findViewById(R.id.searchEt);
        progressBar = view.findViewById(R.id.progressBar);
        sortBtn = view.findViewById(R.id.sortBtn);
        recyclerView = view.findViewById(R.id.recyclerProductsStore);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        starAdapter = new StarAdapter(getContext(),list);
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


        PopupMenu popupMenu = new PopupMenu(getContext(), sortBtn);
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

        cakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),Cakes.class);
                startActivity(intent);
            }
        });

        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Drinks.class);
                startActivity(intent);
            }
        });

        cupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Cupcakes.class);
                startActivity(intent);
            }
        });

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CustomCakes.class);
                startActivity(intent);
            }
        });

        donuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Donuts.class);
                startActivity(intent);
            }
        });





        return view;
    }

    private void loadStoreData() {
        progressBar.setVisibility(View.VISIBLE);

        list.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){


                    Products products = dataSnapshot.getValue(Products.class);
                    list.add(products);

                }
                starAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressBar.setVisibility(View.GONE);

    }

    public class SortProductsAsc implements Comparator<Products>{

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