package com.example.quanly;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.quanly.Model.BookingReference;
import com.example.quanly.View.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static com.example.quanly.MyApplication.CHANNEL_ID;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Running","My Service onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        newOrder();
        //startForeground(1,new NotificationCompat.Builder(this,CHANNEL_ID).build());
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Stop","My Service onDestroy");
    }
    private void newOrder(){
        Intent i= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null){
            DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("bookingreference");
            mData.orderByChild("status").equalTo("Chờ duyệt").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String nameResidence= snapshot.getValue(BookingReference.class).getNameResidence();
                    Notification notification= new NotificationCompat.Builder(getBaseContext(),CHANNEL_ID)
                            .setContentTitle("CÓ HÓA ĐƠN ĐẶT PHÒNG MỚI!")
                            .setContentText(nameResidence+" mới được đặt.")
                            .setSmallIcon(R.drawable.ic_baseline_notifications_24_white)
                            .setColor(getResources().getColor(R.color.blue))
                            .build();
                    notificationManager.notify((int) new Date().getTime(),notification);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
