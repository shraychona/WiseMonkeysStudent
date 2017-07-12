package com.shray.wisemonkeysstudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.R;
import com.shray.wisemonkeysstudent.model.Request;
import com.shray.wisemonkeysstudent.ui.ChatActivity;
import com.shray.wisemonkeysstudent.ui.PaymentActivity;

import java.util.List;

/**
 * Created by Shray on 5/14/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    public List<Request> requestsList;
    Context mContext;
    private static String TAG = "Request Adapter";
    FirebaseAuth mAuth;
    DatabaseReference mRef;

    public RequestAdapter(Context context, List<Request> teachersList) {
        this.mContext = context;
        this.requestsList = teachersList;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_row, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mAuth = FirebaseAuth.getInstance();
        mRef=FirebaseDatabase.getInstance().getReference().child("teacherRequest");

        return new MyViewHolder(itemView);
    }
        @Override
        public void onBindViewHolder(final RequestAdapter.MyViewHolder holder, int position) {



            mAuth = FirebaseAuth.getInstance();
           final Request request=requestsList.get(position);

            final String chatName=request.getStudentUid()+"_"+request.getTutorUid();
            final String requestKey=request.getKey();
            holder.mStudentNameTextView.setText(request.getTutorName());
            holder.mStatus.setText(request.getStatus());
            holder.mLevel.setText(request.getLanguageLevel());
            String statusCheck;
            statusCheck=request.getStatus();
            System.out.println("test"+request.getStatus());
            if (statusCheck.equals("Accepted")){
                holder.mCancelRequest.setText("Proceed To Payment");
                holder.mCancelRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, PaymentActivity.class);
                        intent.putExtra("tutorUid",request.getTutorUid());
                        intent.putExtra("studentUid",request.getStudentUid());
                        intent.putExtra("tutorName",request.getTutorName());
                        intent.putExtra("studentName",request.getStudenName());
                        intent.putExtra("chatref",chatName);
                        intent.putExtra("requestKey",requestKey);
                        mContext.startActivity(intent);
                    }
                });

            }
            else if (statusCheck.equals("Paid"))       {
                holder.mCancelRequest.setText("Proceed To Chat");
                holder.mCancelRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, ChatActivity.class);
                        intent.putExtra("tutorUid",request.getTutorUid());
                        intent.putExtra("studentUid",request.getStudentUid());
                        intent.putExtra("tutorName",request.getTutorName());
                        intent.putExtra("studentName",request.getStudenName());
                        intent.putExtra("chatref",chatName);
                        intent.putExtra("requestKey",requestKey);
                        mContext.startActivity(intent);
                    }
                });

            }

            else if (statusCheck.equals("Pending"))     {
                holder.mCancelRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: "+requestKey);//ye request key kya hai?
                        mRef.child(requestKey).removeValue();
                    }
                });

            }


    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mStudentNameTextView,mLevel,mStatus;
        public Button mCancelRequest;

        public MyViewHolder(View view) {
            super(view);
            mStudentNameTextView = (TextView) view.findViewById(R.id.requestRowNameTextView);
            mLevel = (TextView) view.findViewById(R.id.requestRowLevelTextView);
            mStatus = (TextView) view.findViewById(R.id.requestRowStatusTextView);
            mCancelRequest = (Button) view.findViewById(R.id.requestRowCancelButton);
        }
    }


}
