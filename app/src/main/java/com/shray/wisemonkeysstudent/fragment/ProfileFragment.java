package com.shray.wisemonkeysstudent.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.R;
import com.shray.wisemonkeysstudent.ui.SignInActivity;


public class ProfileFragment extends Fragment {
    private TextView mNameTextView, mAgeTextView,
        mQualificationTextView, mContactTextView, mNationalityTextView, mEmailTextView;
    private DatabaseReference mDatabase;
    private Button mSignOut;

    private FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        mNameTextView = (TextView) mView.findViewById(R.id.profileNameTextView);
        mAgeTextView = (TextView) mView.findViewById(R.id.profileAgeTextView);
        mQualificationTextView = (TextView) mView.findViewById(R.id.profileQualificationTextView);
        mContactTextView = (TextView) mView.findViewById(R.id.profileContactTextView);
        mNationalityTextView = (TextView) mView.findViewById(R.id.profileNationalityTextView);
        mEmailTextView = (TextView) mView.findViewById(R.id.profileEmailTextView);
        mSignOut= (Button) mView.findViewById(R.id.profileSignOutButton);

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mName = dataSnapshot.child("name").getValue().toString();
                String mAge = dataSnapshot.child("age").getValue().toString();
                String mQualification = dataSnapshot.child("qualification").getValue().toString();
                String mNationality = dataSnapshot.child("nationality").getValue().toString();
                String mContact = dataSnapshot.child("contact").getValue().toString();
                String mEmail = dataSnapshot.child("email").getValue().toString();
                mNameTextView.setText(mName);
                mQualificationTextView.setText(mQualification);
                mAgeTextView.setText(mAge);
                mNationalityTextView.setText(mNationality);
                mContactTextView.setText(mContact);
                mEmailTextView.setText(mEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
