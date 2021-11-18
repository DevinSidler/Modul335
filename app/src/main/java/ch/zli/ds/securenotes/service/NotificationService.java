package ch.zli.ds.securenotes.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ch.zli.ds.securenotes.R;
import ch.zli.ds.securenotes.activity.MainActivity;

public class NotificationService extends IntentService {

    private static final int notification_id = 3;

    public NotificationService(){
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        /*Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Notification");
        builder.setContentText("Body");
        builder.setSmallIcon(R.drawable.ic_launcher_background);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(notification_id, notification);
        System.out.println("Notification");*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getBaseContext(), "notification_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText("Text")
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .build();

        notificationManager.notify(notification_id, notification);
    }

}
