package com.example.sweetbizmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter {

    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    Context context;
    ArrayList<Message> messageList;

    public MessageListAdapter(Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message =  messageList.get(position);



        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();



        Message message =  messageList.get(position);

        if (message.getSender().equals(userID)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage, chatImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            chatImage = itemView.findViewById(R.id.chatImageOther);
            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
            nameText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
            profileImage = (ImageView) itemView.findViewById(R.id.image_gchat_profile_other);
        }

        void bind(Message message) {

            if(message.getMessage().contains("https://firebasestorage.googleapis.com/v0/b/sweetbiz-89782.appspot.com/o/Images%2F")){
                Glide.with(context).load(message.getMessage()).into(chatImage);
            }else{
                messageText.setText(message.getMessage());
            }

            // Format the stored timestamp into a readable String using method.


            timeText.setText(Long.toString(message.getCreatedAt()));
            nameText.setText("SweetBiz");

            // Insert the profile image from the URL into the ImageView.
        //    Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView chatImage;

        SentMessageHolder(View itemView) {
            super(itemView);
            chatImage = itemView.findViewById(R.id.chatImageMe);
            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
        }

        void bind(Message message) {
            if(message.getMessage().contains("https://firebasestorage.googleapis.com/v0/b/sweetbiz-89782.appspot.com/o/Images%2F")){
                Glide.with(context).load(message.getMessage()).into(chatImage);
            }else{
                messageText.setText(message.getMessage());
            }

            // Format the stored timestamp into a readable String using method.
           timeText.setText(Long.toString(message.getCreatedAt()));
        }
    }



}
