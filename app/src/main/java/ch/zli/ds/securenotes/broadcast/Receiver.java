package ch.zli.ds.securenotes.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ch.zli.ds.securenotes.service.NotificationService;

public class Receiver extends BroadcastReceiver {
    public Receiver(){}

    @Override
    public void onReceive(Context context, Intent intent){
        Intent intent1 = new Intent(context, NotificationService.class);
        context.startService(intent1);
    }

}
