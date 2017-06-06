package com.example.easynote.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.activities.CreateActivity;

/**
 * Created by SEVAK on 05.06.2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String string = intent.getStringExtra(CreateActivity.ALARM_KEY_TITLE);
        Toast.makeText(App.getInstance(), string, Toast.LENGTH_SHORT).show();
    }
}
