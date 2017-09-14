package com.ratulbintazul.www.chatbot;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView messageRecycleView;
    public RecyclerView.Adapter messageAdapter;
    public RecyclerView.LayoutManager messageLayoutManager;

    public ArrayList<MessageDataProvider> messageArrayList = new ArrayList<>();
    EditText messageEditText;
    ImageButton messageSendButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference msgRef = database.getReference("message");

    public static final int TEACH_ME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEditText = (EditText)findViewById(R.id.messageEditText);
        messageSendButton = (ImageButton) findViewById(R.id.messageSendBtn);

        messageRecycleView = (RecyclerView) findViewById(R.id.chatViewRecyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        messageRecycleView.setHasFixedSize(true);


        // use a linear layout manager
        messageLayoutManager = new LinearLayoutManager(this);
        messageRecycleView.setLayoutManager(messageLayoutManager);

        // specify an adapter
        messageAdapter = new MessageAdapter(messageArrayList,this);
        messageRecycleView.setAdapter(messageAdapter);


        messageAdapter.notifyDataSetChanged();


        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {
                    final String ques = messageEditText.getText().toString();
                    messageEditText.getText().clear();

                    messageArrayList.add(new MessageDataProvider(ques));

                    //SEND MSG TO FIREBASE

                    msgRef.child(StringRefiner.refineString(ques)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            if (s == null) {
                                Intent i = new Intent(MainActivity.this, TeachMeActivity.class);
                                i.putExtra("ques", ques);
                                startActivityForResult(i, TEACH_ME);
                            } else {
                                messageArrayList.add(new MessageDataProvider(s));
                            }

                            Log.e("msg", ques);

                            Log.e("last", messageArrayList.get(messageArrayList.size() - 1).getMessage() + "last added");
                            scrollDown();
                            // Log.e("msg",msg.getAns());
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                    final ViewTreeObserver vto = messageRecycleView.getViewTreeObserver();
                    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            messageRecycleView.scrollToPosition(messageArrayList.size());
                            if (vto.isAlive()) {
                                // Unregister the listener to only call scrollToPosition once
                                vto.removeGlobalOnLayoutListener(this);
                                // Use vto.removeOnGlobalLayoutListener(this) on API16+ devices as
                                // removeGlobalOnLayoutListener is deprecated.
                                // They do the same thing, just a rename so your choice.
                                Log.e("last", messageArrayList.get(messageArrayList.size() - 1).getMessage() + "second last");
                            }
                        }
                    });
//                Query query = msgRef.child(ques).equalTo(StringRefiner.refineString(ques));
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        Message msg = null;
//
//                        msg = dataSnapshot.getValue(Message.class);
//
//
//                        if(msg==null)
//                            startActivity(new Intent(MainActivity.this,TeachMeActivity.class));
//                        else
//                            messageArrayList.add(new MessageDataProvider(msg.getAns()));
//
//                        Log.e("msg",ques);
//                        //
//                        // Log.e("msg",msg.getAns());
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

                    scrollDown();
                    Log.e("last", messageArrayList.get(messageArrayList.size() - 1).getMessage() + "last msg");
                }else {
                    //network not available
                    messageArrayList.add(new MessageDataProvider("there is no Internet connection.\nI can't connect to my brain sorry. :p"));
                    scrollDown();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==TEACH_ME) {
            if(resultCode==RESULT_OK) {
                messageArrayList.add(new MessageDataProvider(data.getStringExtra("saved_ques")));
                scrollDown();
            }
        }
    }

    public void scrollDown() {
        messageAdapter.notifyDataSetChanged();
        messageAdapter.notifyItemInserted(messageAdapter.getItemCount());
        messageRecycleView.swapAdapter(messageAdapter,false);
        messageRecycleView.smoothScrollToPosition(messageRecycleView.getAdapter().getItemCount()-1);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
