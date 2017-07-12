package com.shray.wisemonkeysstudent.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.R;
import com.shray.wisemonkeysstudent.adapter.TeacherAdapter;
import com.shray.wisemonkeysstudent.model.Teachers;
import com.shray.wisemonkeysstudent.ui.LanguageSelectionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private Spinner mLanguageSpinner;
    private String uid;
    private String[] languages = {"English", "Spanish"};
    String mLanguage;
    RecyclerView mRecyclerView;
    TeacherAdapter mTeacherAdapter;
    List<Teachers> teachersList = new ArrayList<>();
    AppCompatButton getTeachers;
    DatabaseReference mTeacherRef;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_search, container, false);

        mLanguageSpinner = (Spinner) mView.findViewById(R.id.languageLanguageSpinner);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        getTeachers = (AppCompatButton) mView.findViewById(R.id.getTeachers);
        mTeacherRef = FirebaseDatabase.getInstance().getReference().child("languages");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mLanguage = languages[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mLanguage = languages[0];
            }
        });
        ArrayAdapter languageAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mLanguageSpinner.setAdapter(languageAdapter);

        getTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teachersList.clear();
                DatabaseReference newRef = mTeacherRef.child(mLanguage);
                System.out.println(mLanguage);
                newRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("test");
                        System.out.println(dataSnapshot.getValue().toString());
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                            try {
                                System.out.println(childDataSnapshot.getValue().toString());
                                teachersList.add(new Teachers(childDataSnapshot.child("name").getValue().toString(),
                                        childDataSnapshot.child("age").getValue().toString(),
                                        childDataSnapshot.child("qualification").getValue().toString(),
                                        childDataSnapshot.child("language").getValue().toString(),
                                        childDataSnapshot.child("nationality").getValue().toString(),
                                        childDataSnapshot.child("uid").getValue().toString(),
                                        childDataSnapshot.child("latitude").getValue().toString(),
                                        childDataSnapshot.child("longitude").getValue().toString()
                                        ));
                            } catch (Exception e) {
                                //  teachersList.clear();
                                e.printStackTrace();
                            }

                        }

                        mTeacherAdapter = new TeacherAdapter(getActivity(), teachersList);
                        System.out.println(teachersList);
                        mRecyclerView.setAdapter(mTeacherAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        });

        return mView ;
    }

}
