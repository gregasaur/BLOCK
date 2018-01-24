package com.example.thea.app_list;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import java.util.concurrent.TimeUnit;


public class MyService extends Service {

    private Vibrator v;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 71399;
    @Override
    public void onCreate() {
        super.onCreate();

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences sharedPreferences = getSharedPreferences("Timer", Context.MODE_PRIVATE);
        int dur = sharedPreferences.getInt("duration", 0);

        //background timer
        CountDownTimer countDownTimer = new CountDownTimer(dur,    1000) {
            @Override
            public void onTick(long dur) {
                long millis= dur;
                String hms= String.format("%02d:%02d:%02d",

                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
                        //seconds
                        ,TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );
                //startnotif(hms);
                startForeground(uniqueID, buildForegroundNotification(hms));
            }

            @Override
            public void onFinish() {
                long n[] = {1,1000,500,1000,500,1000,500,1000,500,1000,500,1000,500,1000,500,1000,500,1000};
                v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(n, -1);
                endnotif();
            }
        };
        countDownTimer.start();

        return START_STICKY;
    }

    public void onDestroy() {
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void endnotif(){
        notification.setSmallIcon(R.drawable.openlock);
        notification.setContentText("00:00");
        notification.setTicker("apps are now unblocked!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("You survived!");
        notification.setContentText("Apps are now unblocked!");
        ClickNotif();
    }

    //other parts of  notif
    public void ClickNotif(){
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
        onDestroy();
    }

    private Notification buildForegroundNotification(String hms) {
        NotificationCompat.Builder b=new NotificationCompat.Builder(this);
        b.setOngoing(true);
        b.setContentTitle("Be Productive!")
                .setContentText(hms)
                .setSmallIcon(R.drawable.closedlock)
                .setTicker("Apps are now Blocked");
        return(b.build());
    }

}


