package com.ashish.results.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ashish.results.services.ResultsNotificationService;

/**
 * Created by snowpuppet on 22/07/16.
 */
public class BootCompletedAutoStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, ResultsNotificationService.class);
            context.startService(serviceIntent);
        }
    }
}
