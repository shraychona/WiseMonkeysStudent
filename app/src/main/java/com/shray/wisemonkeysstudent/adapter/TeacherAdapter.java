package com.shray.wisemonkeysstudent.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shray.wisemonkeysstudent.R;

import com.shray.wisemonkeysstudent.model.Teachers;
import com.shray.wisemonkeysstudent.ui.TutorProfileActivity;

import java.util.List;

/**
 * Created by Shray on 5/13/2017.
 */
public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> {
    public List<Teachers> teachersList;
    Context mContext;
    private static String TAG="Teacher Adapter";
    private float distanceInKm;


    public TeacherAdapter(Context context, List<Teachers> teachersList) {
        this.mContext = context;
        this.teachersList = teachersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Teachers teachers = teachersList.get(position);
        holder.mName.setText(teachers.getName());
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(teachers.getLattitude()));
        startPoint.setLongitude(Double.parseDouble(teachers.getLongitude()));

        Location endPoint=new Location("locationA");
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        Double mLatitude=latitude;
        Double mLongitude=longitude;
        endPoint.setLatitude(mLatitude);
        endPoint.setLongitude(mLongitude);
        double distance=startPoint.distanceTo(endPoint);
        distanceInKm= (float) (distance/1000);

        holder.mDistance.setText("You Are "+String.valueOf(distanceInKm)+" Km Away");
        holder.mNationality.setText(teachers.getNationality());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,TutorProfileActivity.class);
                intent.putExtra("uid",teachers.getUid());
                intent.putExtra("distance",distanceInKm+"");
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teachersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mName,mNationality, mDistance;

        public MyViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.tutorRowNameTextView);
            mNationality = (TextView) view.findViewById(R.id.tutorRowRatingtextView);
            mDistance =(TextView)view.findViewById(R.id.tutorRowDistanceTextView);
        }
    }

}
