package com.linnap.wakeupexperiment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @SuppressWarnings("unused")
    public void onStart(View view) {
        long intervalMillis = 1000 * getLongValue(R.id.wakeup_interval, "Wakeup interval");
        long awakeMillis = 1000 * getLongValue(R.id.awake_for, "Awake for");

        if (intervalMillis > 0 && awakeMillis > 0)
            AlarmReceiver.startRepeating(this, intervalMillis, awakeMillis);
    }

    @SuppressWarnings("unused")
    public void onStop(View view) {
        AlarmReceiver.stopRepeating(this);
    }

    private long getLongValue(int viewId, String errorFieldName) {
        String text = ((EditText)findViewById(viewId)).getText().toString();
        try {
            long value = Long.parseLong(text);
            if (value > 0) {
                return value;
            } else {
                Toast.makeText(this, errorFieldName + " must be positive.", Toast.LENGTH_SHORT).show();
                return 0;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, errorFieldName + " is not a valid number", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}
