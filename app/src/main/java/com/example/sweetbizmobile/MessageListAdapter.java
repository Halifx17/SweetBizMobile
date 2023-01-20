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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageListAdapter extends RecyclerView.Adapter {

    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userID;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_IMAGE_SENT = 3;
    private static final int VIEW_TYPE_MESSAGE_IMAGE_RECEIVED = 4;

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
        }else if (viewType == VIEW_TYPE_MESSAGE_IMAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_image_sent, parent, false);
            return new SentMessageImageHolder(view);
        }else if (viewType == VIEW_TYPE_MESSAGE_IMAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_image_received, parent, false);
            return new ReceivedMessageImageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message =  messageList.get(position);
        String pattern = "MMM. dd, yyyy, hh:mm a";
        DateFormat df = new SimpleDateFormat(pattern);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                SentMessageHolder sentMessageHolder = (SentMessageHolder) holder;
                sentMessageHolder.messageText.setText(message.getMessage());
                String timeMessageSent = df.format(message.getCreatedAt()*1000);
                sentMessageHolder.timeText.setText(timeMessageSent);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ReceivedMessageHolder receivedMessageHolder = (ReceivedMessageHolder) holder;
                receivedMessageHolder.messageText.setText(message.getMessage());
                receivedMessageHolder.nameText.setText("SweetBiz");
                String timeMessageReceived = df.format(message.getCreatedAt()*1000);
                receivedMessageHolder.timeText.setText(timeMessageReceived);
                break;
            case VIEW_TYPE_MESSAGE_IMAGE_SENT:
                SentMessageImageHolder sentMessageImageHolder = (SentMessageImageHolder) holder;
                Glide.with(context).load(message.getMessage()).into(sentMessageImageHolder.chatImage);
                String timeMessageImageSent = df.format(message.getCreatedAt()*1000);
                sentMessageImageHolder.timeText.setText(timeMessageImageSent);
                break;
            case VIEW_TYPE_MESSAGE_IMAGE_RECEIVED:
                ReceivedMessageImageHolder receivedMessageImageHolder = (ReceivedMessageImageHolder) holder;
                Glide.with(context).load(message.getMessage()).into(receivedMessageImageHolder.chatImage);
                receivedMessageImageHolder.nameText.setText("SweetBiz");
                String timeMessageImageReceived= df.format(message.getCreatedAt()*1000);
                receivedMessageImageHolder.timeText.setText(timeMessageImageReceived);
                break;
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

        if (message.getSender().equals(userID) && !message.getMessage().contains("https://firebasestorage.googleapis.com/v0/b/sweetbiz-89782.appspot.com/o/Images%2F")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        }else if(message.getSender().equals(userID) && message.getMessage().contains("https://firebasestorage.googleapis.com/v0/b/sweetbiz-89782.appspot.com/o/Images%2F")){
            return VIEW_TYPE_MESSAGE_IMAGE_SENT;

        }else if(message.getSender().equals("SweetBiz") && message.getMessage().contains("https://firebasestorage.googleapis.com/v0/b/sweetbiz-89782.appspot.com/o/Images%2F")){
            return VIEW_TYPE_MESSAGE_IMAGE_RECEIVED;
        }else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }


    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText =  itemView.findViewById(R.id.text_gchat_message_other);
            timeText =  itemView.findViewById(R.id.text_gchat_timestamp_other);
            nameText = itemView.findViewById(R.id.text_gchat_user_other);
            profileImage = itemView.findViewById(R.id.image_gchat_profile_other);
        }


    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, dateText;

        SentMessageHolder(View itemView) {
            super(itemView);

            dateText = itemView.findViewById(R.id.text_gchat_user_me_date);
            messageText = itemView.findViewById(R.id.text_gchat_message_me);
            timeText = itemView.findViewById(R.id.text_gchat_timestamp_me);
        }


    }

    private class ReceivedMessageImageHolder extends RecyclerView.ViewHolder{
        TextView messageText, timeText, nameText;
        ImageView profileImage, chatImage;

        public ReceivedMessageImageHolder(@NonNull View itemView) {
            super(itemView);
            chatImage = itemView.findViewById(R.id.chatImageOther);
            messageText = itemView.findViewById(R.id.text_gchat_message_other);
            timeText =  itemView.findViewById(R.id.text_gchat_timestamp_other);
            nameText =  itemView.findViewById(R.id.text_gchat_user_other);
            profileImage =  itemView.findViewById(R.id.image_gchat_profile_other);
        }
    }

    private class SentMessageImageHolder extends RecyclerView.ViewHolder{
        TextView messageText, timeText, dateText;
        ImageView chatImage;

        public SentMessageImageHolder(@NonNull View itemView) {
            super(itemView);
            chatImage = itemView.findViewById(R.id.chatImageMe);
            dateText = itemView.findViewById(R.id.text_gchat_user_image_me_date);
            messageText =  itemView.findViewById(R.id.text_gchat_message_me);
            timeText = itemView.findViewById(R.id.text_gchat_timestamp_me);
        }
    }



}
