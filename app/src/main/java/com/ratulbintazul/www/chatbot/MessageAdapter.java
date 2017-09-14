package com.ratulbintazul.www.chatbot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 9/12/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.DraftHolder> {

    private ArrayList<MessageDataProvider> arrayList;
    private Context context;

    public MessageAdapter(ArrayList<MessageDataProvider> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.context = ctx;
    }

    @Override
    public MessageAdapter.DraftHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_layout,parent,false);

        MessageAdapter.DraftHolder draftHolder = new MessageAdapter.DraftHolder(view);
        return draftHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.DraftHolder holder, int position) {

        MessageDataProvider messageDataProvider = arrayList.get(position);


        //holder.message.setText(messageDataProvider.getMessage());
        //holder.messageSent.setText(messageDataProvider.getMessage());

        if(position%2==0) {
            holder.messageSent.setText(messageDataProvider.getMessage());
            holder.messageSent.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.GONE);
        }else {
            holder.message.setText(messageDataProvider.getMessage());
            holder.message.setVisibility(View.VISIBLE);
            holder.messageSent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DraftHolder extends RecyclerView.ViewHolder {
        TextView message,messageSent;
        public DraftHolder(final View itemView) {
            super(itemView);
            messageSent = (TextView)itemView.findViewById(R.id.messageSentBox);
            message = (TextView)itemView.findViewById(R.id.messageBox);

        }

    }
}

