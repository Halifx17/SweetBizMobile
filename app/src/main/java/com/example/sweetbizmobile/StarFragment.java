package com.example.sweetbizmobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class StarFragment extends Fragment {

    private static final String DEFAULT_IMG = "https://firebasestorage.googleapis.com/v0/b/sweetbiz-89782.appspot.com/o/Images%2Fdefault-image.jpg?alt=media&token=";

    RecyclerView recyclerView;
    ArrayList<Products> list;
    DatabaseReference databaseReference;
    StarAdapter starAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StarFragment newInstance(String param1, String param2) {
        StarFragment fragment = new StarFragment();
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
        View view = inflater.inflate(R.layout.fragment_star,container,false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = view.findViewById(R.id.starRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("FinishedProducts");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        starAdapter = new StarAdapter(getContext(),list);
        recyclerView.setAdapter(starAdapter);

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                RearrangeItems();


            }
        });




        return view;


    }




    public void RearrangeItems(){
        Collections.shuffle(list);
        starAdapter = new StarAdapter(getContext(),list);
        recyclerView.setAdapter(starAdapter);
    }
}