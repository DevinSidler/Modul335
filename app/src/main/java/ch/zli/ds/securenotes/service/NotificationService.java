package ch.zli.ds.securenotes.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.activity.MainActivity;

public class NotificationService extends IntentService {

    public NotificationService(){
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent){

        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "Reminder";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        Notification notification = new Notification.Builder(this)
                .setContentTitle(intent.getStringExtra("description"))
                .setContentText("")
                .setSmallIcon(R.mipmap.ic_note_app_round)
                .setChannelId(CHANNEL_ID)
                .build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);

        mNotificationManager.notify(notifyID , notification);
    }

}
