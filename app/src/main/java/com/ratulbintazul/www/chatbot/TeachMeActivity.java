package com.ratulbintazul.www.chatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeachMeActivity extends AppCompatActivity {

    EditText ques,ans;
    Button submit;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = db.getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_me);

        ans = (EditText)findViewById(R.id.ansEditText);
        ques = (EditText)findViewById(R.id.quesEditText);

        String q = getIntent().getStringExtra("ques");
        ques.setText(q);

        submit = (Button) findViewById(R.id.teachMeSubmitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child(StringRefiner.refineString(ques.getText().toString())).setValue(ans.getText().toString());
                Intent i = new Intent();
                i.putExtra("saved_ques",ques.getText().toString());
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}
