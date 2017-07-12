package com.shray.wisemonkeysstudent.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shray.wisemonkeysstudent.BaseActivity;
import com.shray.wisemonkeysstudent.R;

import java.util.HashMap;

import io.paperdb.Paper;

public class StudentSignUpActivity extends BaseActivity {

    private static final String TAG ="STUDENT_SIGN_UP   " ;

    private EditText mEmailEditText,mQualificationEditText,mAgeEditText,mContactEditText,mNameEditText,
            mPasswordEditText,mNationalityEditText;
    private Button mSignUpBtn;



    private DatabaseReference mDatabase;;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Wise Monkeys");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEmailEditText= (EditText) findViewById(R.id.studentEmailEditText);
        mPasswordEditText= (EditText) findViewById(R.id.studentPasswordEditText);
        mNameEditText= (EditText) findViewById(R.id.studentNameEditText);
        mContactEditText= (EditText) findViewById(R.id.studentContactEditText);
        mAgeEditText= (EditText) findViewById(R.id.studentAgeEditText);
        mQualificationEditText= (EditText) findViewById(R.id.studentQualificationEditText);
        mNationalityEditText= (EditText) findViewById(R.id.studentNationalityEditText);
        mSignUpBtn= (Button) findViewById(R.id.studentSignUpBtn);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mEmail=mEmailEditText.getText().toString().trim();
                String mPassword=mPasswordEditText.getText().toString().trim();


                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(StudentSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(StudentSignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
                
            }
        });
        

    }

    private void updateUI(FirebaseUser user) {
        String mName=mNameEditText.getText().toString().trim();
        String mContact=mContactEditText.getText().toString().trim();
        String mAge=mAgeEditText.getText().toString().trim();
        String mQualification= mQualificationEditText.getText().toString().trim();
        String mNationality=mNationalityEditText.getText().toString().trim();
        String uid =user.getUid();
        String mEmail = user.getEmail();

        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("email",mEmail);
        dataMap.put("name",mName);
        dataMap.put("contact",mContact);
        dataMap.put("age",mAge);
        dataMap.put("qualification",mQualification);
        dataMap.put("nationality",mNationality);
        dataMap.put("language","Null");
        dataMap.put("languageType","Null");
        dataMap.put("profileCode","2");
        dataMap.put("balance","0");
        dataMap.put("uid",mAuth.getCurrentUser().getUid());
        Paper.init(this);
        String token = Paper.book().read("token","invalid");
        dataMap.put("token",token);
        mDatabase.child("users").child(uid).setValue(dataMap);

        Intent intent =new Intent(StudentSignUpActivity.this, LanguageSelectionActivity.class);
        startActivity(intent);
    }
}
