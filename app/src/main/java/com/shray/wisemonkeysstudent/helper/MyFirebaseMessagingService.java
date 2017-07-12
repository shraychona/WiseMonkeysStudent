package com.shray.wisemonkeysstudent.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.shray.wisemonkeysstudent.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shray.wisemonkeysstudent.ui.LanguageSelectionActivity;
import com.shray.wisemonkeysstudent.ui.PaymentActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: ");
        showNotification(remoteMessage.getData().get("message"));

    }

    private void showNotification(String message) {
        Intent intent=new Intent(this, PaymentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Notes Guru")
                .setContentText(message)
                .setSmallIcon(R.drawable.happy)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.happy))
                .setContentIntent(pendingIntent);

        NotificationManager mNotification =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification.notify((int) System.currentTimeMillis(),builder.build());




    }
}
