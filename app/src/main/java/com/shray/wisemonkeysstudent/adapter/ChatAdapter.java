package com.shray.wisemonkeysstudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shray.wisemonkeysstudent.R;
import com.shray.wisemonkeysstudent.model.Chat;

import java.util.List;

/**
 * Created by Shray on 5/14/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context mContext;
    List<Chat> messageList;
    FirebaseAuth mAuth;
    String CurrentUserName;
    public ChatAdapter(Context context, List<Chat> messageList){
        this.mContext=context;
        this.messageList=messageList;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ChatAdapter.MyViewHolder holder, final int position) {
        mAuth=FirebaseAuth.getInstance();
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentUserName=dataSnapshot.child("name").getValue().toString();
                Chat chat=messageList.get(position);
                holder.mName.setText(chat.getName());
                holder.mMessage.setText(chat.getMessage());

                if (chat.getName().equals(CurrentUserName)){

                    holder.ll.setGravity(Gravity.END);
                    holder.chatCardView.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName,mMessage;
        LinearLayout ll;
        CardView chatCardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mName=(TextView)itemView.findViewById(R.id.name);
            mMessage=(TextView)itemView.findViewById(R.id.message);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            chatCardView= (CardView) itemView.findViewById(R.id.chatCardLayout);
        }
    }
}
