package com.shray.wisemonkeysstudent.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.BaseActivity;
import com.shray.wisemonkeysstudent.R;


public class SignInActivity extends BaseActivity {

    private static final String TAG = "SIGN_IN_ACTIVITY" ;

    private EditText mEmailEditText,mPasswordEditText;
    private Button mSignInBtn,mStudentIntentBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Wise Monkeys");
        mProgressDialog=new ProgressDialog(this);
        mEmailEditText = (EditText) findViewById(R.id.signInEmailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.signInPasswordEditText);
        mSignInBtn = (Button) findViewById(R.id.signInSignInBtn);
        mStudentIntentBtn = (Button) findViewById(R.id.signInStudentBtn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            updateUi();

        } else {

            mStudentIntentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentTutor = new Intent(SignInActivity.this, StudentSignUpActivity.class);
                    startActivity(intentTutor);
                }
            });

            mSignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mEmail = mEmailEditText.getText().toString().trim();
                    String mPassword = mPasswordEditText.getText().toString().trim();

                    mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        updateUi();


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

        }
    }

    private void updateUi() {
       mProgressDialog.setMessage("Signing In ...");
        mProgressDialog.show();
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mProfileCode = dataSnapshot.child("profileCode").getValue().toString();
                String mName = dataSnapshot.child("name").getValue().toString();

                if (mProfileCode.equals("2")) {
                    mProgressDialog.dismiss();
                    Intent intent = new Intent(SignInActivity.this, LanguageSelectionActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
}
