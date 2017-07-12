package com.shray.wisemonkeysstudent.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shray.wisemonkeysstudent.BaseActivity;
import com.shray.wisemonkeysstudent.R;

public class PaymentActivity extends BaseActivity {

    private Button mPayButton;
    String mTutorUid,mStudentUid,mStudentName,mTutorName,chatKey,requestKey;
    FirebaseAuth mAuth;
    DatabaseReference mReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAuth = FirebaseAuth.getInstance();


        mPayButton= (Button) findViewById(R.id.paymentPayButton);
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActionBar actionBar=getSupportActionBar();
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setTitle("Wise Monkeys");

                Intent getIntent=getIntent();
                mTutorUid=getIntent.getStringExtra("tutorUid");
                mStudentName=getIntent.getStringExtra("studentName");
                mStudentUid=getIntent.getStringExtra("studentUid");
                mTutorName=getIntent.getStringExtra("tutorName");
                chatKey=getIntent.getStringExtra("chatref");
                requestKey=getIntent.getStringExtra("requestKey");
                String chatName=mStudentUid+"_"+mTutorUid;
                mReference=FirebaseDatabase.getInstance().getReference().child("teacherRequest");
                mReference.child(requestKey).child("status").setValue("Paid");
                Intent putIntent=new Intent(PaymentActivity.this,ChatActivity.class);
                putIntent.putExtra("studentName",mStudentName);
                putIntent.putExtra("chatref",chatName);
                putIntent.putExtra("requestKey",requestKey);
                putIntent.putExtra("tutorUid",mTutorUid);
                startActivity(putIntent);
            }
        });
    }
}
