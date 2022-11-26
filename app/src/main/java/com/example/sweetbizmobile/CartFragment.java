package com.example.sweetbizmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements QuantityListener{


    RecyclerView recyclerView;
    ArrayList<CartProducts> list;
    DatabaseReference databaseReference, cartDatabase;
    CartAdapter cartAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    FirebaseUser user;
    FirebaseAuth mAuth;

    String userID;

    Button deleteBtn;
    ExtendedFloatingActionButton checkOutBtn;

    CheckBox selectAllCheckBox;
    TextView cartProductTotalPrice;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart,container,false);

        cartProductTotalPrice = view.findViewById(R.id.cartProductTotalPrice);

        selectAllCheckBox = view.findViewById(R.id.cartProductSelectAllCheckBox);
        checkOutBtn = view.findViewById(R.id.checkOutBtn);

        deleteBtn = view.findViewById(R.id.deleteBtn);
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        userID = user.getUid();

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerCart);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(getContext(),list,this);
        recyclerView.setAdapter(cartAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(userID);




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){


                    CartProducts cartProducts = dataSnapshot.getValue(CartProducts.class);
                    list.add(cartProducts);

                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);


            }
        });



        selectAllCheckBox.setChecked(true);




        return view;


    }


    @Override
    public void onQuantityChange(ArrayList<String> arrayList) {

        cartDatabase = FirebaseDatabase.getInstance().getReference("Carts").child(userID);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemNoID;

                for(int i = arrayList.size()-1; i >= 0; i--){
                    itemNoID = arrayList.get(i);
                    cartDatabase.child(itemNoID).removeValue();
                }

                list.clear();
                cartAdapter.notifyDataSetChanged();

                Intent intent = new Intent(getContext(),Home.class);
                String fragment = "cartFragmentRefresh";
                intent.putExtra("fragment",fragment);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);


             //   Toast.makeText(getContext(),arrayList.toString(),Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getContext(),Integer.toString(arrayList.size()),Toast.LENGTH_SHORT).show();

            }
        });

        Log.d("CheckBox", arrayList.toString());

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });



    }

    @Override
    public void onCheckBoxChange(ArrayList<CheckBox> arrayList) {

        selectAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(selectAllCheckBox.isChecked()){
                    cartAdapter.checkBoxOperation(arrayList,true);
                   //  cartAdapter.selectAll();
                    //  recyclerView.smoothScrollToPosition(cartAdapter.getItemCount() - 1);
                }else{
                    cartAdapter.checkBoxOperation(arrayList,false);
                    //  cartAdapter.unselectAll();
                    //  recyclerView.smoothScrollToPosition(0);
                }

            }
        });


    }
}