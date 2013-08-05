package com.linnap.wakeupexperiment;

import android.content.Context;
import android.os.PowerManager;

public class SharedWakelock {

    private static SharedWakelock instance;
    private PowerManager.WakeLock wakeLock;

    public static SharedWakelock getInstance(Context context) {
        if (instance == null) {
            instance = new SharedWakelock(context);
        }
        return instance;
    }

    public SharedWakelock(Context context) {
        wakeLock = ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "sharedwakelock");
        wakeLock.setReferenceCounted(false);
    }

    public void acquire() {
        wakeLock.acquire();
    }
    public void release() {
        wakeLock.release();
    }
}
