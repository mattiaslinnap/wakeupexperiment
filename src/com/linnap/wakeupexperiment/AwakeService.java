package com.linnap.wakeupexperiment;

import android.app.IntentService;
import android.content.Intent;

public class AwakeService extends IntentService {
    public AwakeService() {
        super("AwakeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long awakeMillis = intent.getLongExtra("awakeMillis", 0);
        if (awakeMillis <= 0)
            throw new IllegalArgumentException("Expected positive awakeMillis, got " + awakeMillis);

        try {
            Thread.sleep(awakeMillis);
        } catch (InterruptedException e) {
            // TODO: perhaps log this.
        } finally {
            SharedWakelock.getInstance(this).release();
        }
    }
}
