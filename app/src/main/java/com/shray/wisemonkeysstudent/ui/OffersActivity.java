package com.shray.wisemonkeysstudent.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.R;
import com.shray.wisemonkeysstudent.adapter.OfferAdapter;
import com.shray.wisemonkeysstudent.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class OffersActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    List<Offer> offerList=new ArrayList<>();
    Query mReference;
    OfferAdapter mOfferAdapter;

    public OffersActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        mRecyclerView= (RecyclerView) findViewById(R.id.offersRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OffersActivity.this));

        mReference = FirebaseDatabase.getInstance().getReference().child("Offers");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                offerList.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    offerList.add(new Offer(childDataSnapshot.child("name").getValue().toString(),
                            childDataSnapshot.child("offer").getValue().toString()));
                    System.out.println(childDataSnapshot.child("name").getValue().toString());
                    System.out.println(childDataSnapshot.child("offer").getValue().toString());

                }
                mOfferAdapter=new OfferAdapter(OffersActivity.this,offerList);
                System.out.println(offerList);
                mRecyclerView.setAdapter(mOfferAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
