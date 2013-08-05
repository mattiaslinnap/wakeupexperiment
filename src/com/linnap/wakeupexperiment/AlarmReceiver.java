package com.linnap.wakeupexperiment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    public static void startRepeating(Context context, long intervalMillis, long awakeMillis) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + intervalMillis, intervalMillis, getPendingIntent(context, awakeMillis));
    }

    public static void stopRepeating(Context context) {
        PendingIntent pendingIntent = getPendingIntent(context, 0);  // awakeMillis value does not matter here - extra data is not used for intent equality.
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Note that the CPU may go to sleep as soon as onReceive() finishes - even before the service can start running.
        // Therefore the wakelock must be shared with AwakeService and taken before onReceive() finishes.
        SharedWakelock.getInstance(context).acquire();
        Intent background = new Intent(context, AwakeService.class);
        background.putExtra("awakeMillis", intent.getLongExtra("awakeMillis", 0));
        context.startService(background);
    }

    private static PendingIntent getPendingIntent(Context context, long awakeMillis) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("awakeMillis", awakeMillis);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
