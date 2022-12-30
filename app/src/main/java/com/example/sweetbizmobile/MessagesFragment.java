package com.example.sweetbizmobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Message> list;
    DatabaseReference databaseReference, chatsDatabaseReference, chatMessagesDatabaseReference;
    MessageListAdapter messageListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    FirebaseUser user;
    FirebaseAuth mAuth;

    String userID;

    String messageTxt;
    DateFormat dateFormat;
    String stringDate;
    Long longDate;
    Long trimLongDate;
    Long timeSort;

    Button sendBtn;
    EditText editMessage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
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
        View view =  inflater.inflate(R.layout.fragment_messages, container, false);



        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats").child(userID);
        chatsDatabaseReference = FirebaseDatabase.getInstance().getReference("Chats").child(userID);
        chatMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("ChatMessages").child(userID);


        sendBtn = view.findViewById(R.id.button_gchat_send);
        editMessage = view.findViewById(R.id.edit_gchat_message);

        recyclerView = view.findViewById(R.id.recycler_gchat);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageListAdapter = new MessageListAdapter(getContext(),list);
        recyclerView.setAdapter(messageListAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){


                    Message message = dataSnapshot.getValue(Message.class);
                    list.add(message);

                }
                messageListAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(list.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateFormat = new SimpleDateFormat("d-MMM-yyyy h:mm a");
                stringDate = dateFormat.format(Calendar.getInstance().getTime());
                longDate = new Date().getTime();
                trimLongDate = longDate/1000;
                timeSort = -trimLongDate;


                messageTxt = editMessage.getText().toString().trim();
                Message message = new Message(messageTxt,userID,trimLongDate);
                MessagesChat messageschat = new MessagesChat(messageTxt,userID,trimLongDate,timeSort);

                chatsDatabaseReference.push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {



                            Log.d("Success", "Success");

                        } else {

                            Log.d("Failed", "Failed");

                        }



                    }
                });

                chatMessagesDatabaseReference.setValue(messageschat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {



                            Log.d("Success", "Success");

                        } else {

                            Log.d("Failed", "Failed");

                        }



                    }
                });


                editMessage.getText().clear();










            }
        });





        return view;
    }
}