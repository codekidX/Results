package com.ashish.results.services;

/*
* Copyright 2016 Ashish Shekar (codekidX)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
* */

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ashish.results.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by snowpuppet on 22/07/16.
 */
public class ResultsNotificationService extends Service {


    public static SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("RES","OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ResultAsync resultAsync = new ResultAsync(sharedPreferences.getInt("which-stream",0),getApplicationContext());
        resultAsync.execute();

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), ResultsNotificationService.class);
        alarmIntent = PendingIntent.getService(getApplicationContext(),0,i,0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        60 * 1000,SystemClock.elapsedRealtime() +
                        60 * 1000, alarmIntent);


        return START_STICKY;
    }


    public boolean isResultAvailable(int stream) throws IOException {

        URL url = new URL(sharedPreferences.getString("mu",""));
        InputStream is = (InputStream) url.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        String htmlContent = sb.toString();

        switch (stream) {
            case 0:
                if(htmlContent.contains("COMPUTER ENGG SEM-VIII ")) {
                    return true;
                } else {
                    return false;
                }
            case 1:
                if(htmlContent.contains("ELECTRONICS ENGG SEM-VIII")) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if(htmlContent.contains("ELECTRONICS AND TELECOMMUNICATION ENGG SEM-VIII")) {
                    return true;
                } else {
                    return false;
                }
            case 3:
                if(htmlContent.contains("INFORMATION TECHNOLOGY SEM-VIII")) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }


    private class ResultAsync extends AsyncTask<Void,Void,Boolean> {

        int stream;
        boolean available;
        Context context;

        public ResultAsync(int stream,Context context) {
            this.stream = stream;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            try {
                available = isResultAvailable(stream);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (available) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle("Results Declared!!")
                        .setSmallIcon(R.drawable.gavel);
                switch (stream) {
                    case 0:
                        builder.setContentText("BE Computer Engg");
                        break;
                    case 1:
                        builder.setContentText("BE Electronics Engg");
                        break;
                }
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(998,builder.build());
            }
            return null;
        }
    }

}
