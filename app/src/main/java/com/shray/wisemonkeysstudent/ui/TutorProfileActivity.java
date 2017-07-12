package com.shray.wisemonkeysstudent.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.BaseActivity;
import com.shray.wisemonkeysstudent.R;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class TutorProfileActivity extends BaseActivity {

    private TextView mNameTextView, mAgeTextView, mQualificationTextView, mLanguageTextView,
            mNationalityTextView, mRatingTextView, mDistanceTextView;
    private Button mProceedButton,mKnowYourLevel;
    private DatabaseReference mDatabase;
    private Spinner mLanguageLevelSpinner;
    private String[] languageLevels={"A1","A1+","A2","B1","B2"};
    private String mLanguageLevel,mDistance;
    FirebaseAuth mAuth;
    private String tutorUid, mUid,mStudentName,tutorToken,studentToken,mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Wise Monkeys");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        tutorUid = intent.getStringExtra("uid");
        mDistance=intent.getStringExtra("distance");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mNameTextView = (TextView) findViewById(R.id.tutorProfileNameTextView);
        mAgeTextView = (TextView) findViewById(R.id.tutorProfileAgeTextView);
        mQualificationTextView = (TextView) findViewById(R.id.tutorProfileQualificationTextView);
        mLanguageTextView = (TextView) findViewById(R.id.tutorProfileLanguageTextView);
        mNationalityTextView = (TextView) findViewById(R.id.tutorProfileNationalityTextView);
        mRatingTextView = (TextView) findViewById(R.id.tutorProfileRatingTextView);
        mDistanceTextView = (TextView) findViewById(R.id.tutorProfileDistanceTextView);
        mProceedButton = (Button) findViewById(R.id.tutorProfileProceedButton);
        mLanguageLevelSpinner= (Spinner) findViewById(R.id.tutorProfileLangugeLevelSpinner);
        mKnowYourLevel= (Button) findViewById(R.id.tutorProfileKnowYourLevelButton);


        mLanguageLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mLanguageLevel = languageLevels[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mLanguageLevel = languageLevels[0];
            }
        });
        ArrayAdapter languageLevelAdapter = new ArrayAdapter(TutorProfileActivity.this, android.R.layout.simple_spinner_item, languageLevels);
        languageLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mLanguageLevelSpinner.setAdapter(languageLevelAdapter);


        mDatabase.child("users").child(tutorUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mName = dataSnapshot.child("name").getValue().toString();
                String mAge = dataSnapshot.child("age").getValue().toString();
                String mQualification = dataSnapshot.child("qualification").getValue().toString();
                String mNationality = dataSnapshot.child("nationality").getValue().toString();
                String mLanguage = dataSnapshot.child("language").getValue().toString();
                tutorToken =dataSnapshot.child("token").getValue().toString();
                mNameTextView.setText(mName);
                mQualificationTextView.setText(mQualification);
                mAgeTextView.setText(mAge);
                mNationalityTextView.setText(mNationality);
                mLanguageTextView.setText(mLanguage);
                mDistanceTextView.setText("You Are "+mDistance+" Km Away");
                mRatingTextView.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mStudentName=dataSnapshot.child("name").getValue().toString();
                studentToken=dataSnapshot.child("token").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mKnowYourLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.transparent.com/language-resources/tests.html"));
                startActivity(browserIntent);
            }
        });


        mProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> dataMap = new HashMap<String, String>();

                dataMap.put("username", mStudentName);
                dataMap.put("useruid", mAuth.getCurrentUser().getUid());
                dataMap.put("tutoruid", tutorUid);
                dataMap.put("studentToken",studentToken);
                dataMap.put("status", "Pending");
                dataMap.put("tutorName",mName);
                dataMap.put("languageLevel",mLanguageLevel);
                //mDatabase.child("studentRequest").push().setValue(dataMap);
                mDatabase.child("teacherRequest").push().setValue(dataMap);
                //sendNotification();
                startActivity(new Intent(TutorProfileActivity.this,LanguageSelectionActivity.class));
            }
        });

    }

    private void sendNotification() {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("UserName", mStudentName)
                .add("Token", tutorToken)
                .build();
        Request request = new Request.Builder()
                .url("http://www.indiagrocerystore.com.au/wisemonkeys/push_notification.php")
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
