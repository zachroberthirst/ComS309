package com.example.frontend.model.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.model.server.Message;
import com.example.frontend.model.user.User;
import com.example.frontend.model.MainApplication;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ChatMessageRecycleAdapter extends RecyclerView.Adapter<ChatMessageRecycleAdapter.MyViewHolder> {

    private final ArrayList<Message> messages;
    private final Context context;
    private final OnNoteListener onNoteListener;

    public ChatMessageRecycleAdapter(ArrayList<Message> messages, Context context, OnNoteListener onNoteListener){
        this.messages = messages;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, parent,false );
        return new MyViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User u = MainApplication.getActiveUser();
        Message m = messages.get(position);
        String content, time;
        content = String.format(Locale.getDefault(),"%s",m.getContent());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(m.getSent()),
                        TimeZone.getDefault().toZoneId());
        //format(DateTimeFormatter.ofPattern("[hma]"))
        time = String.format("%s", localDateTime.format(DateTimeFormatter.ofPattern("[h:ma]")));
        if(localDateTime.getMinute() < 10){
            time = String.format("%s", localDateTime.format(DateTimeFormatter.ofPattern("[h:0ma]")));
        }
        if(u.getUsername().equals(m.getSender())){
            //if this user is the sender
            holder.receivedLayout.setVisibility(View.GONE);
            holder.sentLayout.setVisibility(View.VISIBLE);
            holder.messageSenderContent.setText(content);
            holder.messageSenderTime.setText(time);

        }else if(u.getUsername().equals(m.getReceiver())){
            if(m.isEnd()){
                holder.sentLayout.setVisibility(View.GONE);
                holder.receivedLayout.setVisibility(View.GONE);
                holder.chatUnmatched.setVisibility(View.VISIBLE);
                holder.chatUnmatchedText.setText("Un-matched");
            }else{
                //else if this user is the receiver
                holder.sentLayout.setVisibility(View.GONE);
                holder.receivedLayout.setVisibility(View.VISIBLE);
                holder.messageReceiverContent.setText(content);
                holder.messageReceiverTime.setText(time);
            }
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageSenderContent, messageReceiverContent, messageSenderTime, messageReceiverTime, chatUnmatchedText;
        RelativeLayout sentLayout, receivedLayout, chatUnmatched;
        OnNoteListener mOnNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            messageSenderContent = itemView.findViewById(R.id.sent_message_content);
            messageReceiverContent = itemView.findViewById(R.id.received_message_content);
            messageSenderTime = itemView.findViewById(R.id.sent_message_time);
            messageReceiverTime = itemView.findViewById(R.id.received_message_time);
            sentLayout = itemView.findViewById(R.id.sent);
            receivedLayout = itemView.findViewById(R.id.received);
            chatUnmatched = itemView.findViewById(R.id.chat_unmatched);
            chatUnmatchedText = itemView.findViewById(R.id.chat_unmatched_text);
            chatUnmatched.setVisibility(View.GONE);

            mOnNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnNoteListener.OnNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}
