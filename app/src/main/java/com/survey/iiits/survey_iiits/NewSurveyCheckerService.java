package com.survey.iiits.survey_iiits;

/**
 * Authors : [David Christie]
 * Team : [David Christie,Shyam Sunder]
 * Last Edited : [4/17/2018]
 */


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import android.view.WindowManager;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class NewSurveyCheckerService extends Service {
    BackgroundService service;
    PrefManager sessions;
    String userId;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sessions = new PrefManager(getApplicationContext());
        if (!service.isRunning()) {
            service.start();
            service.isRunning = true;
        }
        userId = sessions.getuserId();
        Log.d("TAG", "onStartCommand: "+userId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d("IIITS", "onStartCommand: STARTED SERVICE CONFIRMED");
        super.onCreate();
        service = new BackgroundService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (service.isRunning) {
            service.interrupt();
            service.isRunning = false;
            service = null;
        }
    }

    class BackgroundService extends Thread {
        public boolean isRunning = false;
        public long milliSecs = 10000;
        Runnable runTask = new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        sendRequest();
                        Thread.sleep(milliSecs);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isRunning = false;
                    }
                }
            }
        };

        public boolean isRunning() {
            return isRunning;
        }

        @Override
        public void run() {
            super.run();
            runTask.run();
        }

        private void sendRequest() {
            loadDetails load = new loadDetails();
            load.execute(userId);
        }
    }

    private class loadDetails extends AsyncTask<String,String,String>
    {
        class Message{
            String senderName;
            int id;
            String note;
            String timestamp;
            String title;
            int forcedResponse;
            int anonymouse;
            Message(int id,String userName,String title,String note,String timestamppost,int forcedResponse,int anonymouse)
            {
                this.id = id;
                this.senderName = userName;
                this.note = note;
                this.title = title;
                this.timestamp = timestamppost;
            this.forcedResponse=forcedResponse;
            this.anonymouse=anonymouse;
            }
        }
        ArrayList<Message> messages = new ArrayList<>();

        @Override
        protected void onPostExecute(String s) {
            int messageNumber = 0;
            int countnum=0;
            for(int i=0;i<messages.size();i++)
            {
               if(messages.get(i).forcedResponse==0){ Intent intent = new Intent(getBaseContext(), Questionaire.class);
                intent.putExtra("firstKeyName","" + messages.get(i).id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pIntent = PendingIntent.getActivity(NewSurveyCheckerService.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                String CHANNEL_ID = "my_channel_01";
                Notification n  = new Notification.Builder(NewSurveyCheckerService.this)
                        .setContentText("New Survey from : " + messages.get(i).senderName)
                        .setContentTitle("Topic :" + messages.get(i).title + "  Note : " + messages.get(i).note)
                        .setSmallIcon(R.drawable.draft_icon)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.main_icon, "View", pIntent).build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                messageNumber++;
                notificationManager.notify(messageNumber, n);
            }else {
                   if (countnum == 0) {
                       countnum++;
                       final int temp = i;
                       new Handler(Looper.getMainLooper()).post(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(getApplicationContext(),
                                       "hai",
                                       Toast.LENGTH_SHORT).show();

                               final AlertDialog alertDialog = new AlertDialog.Builder(NewSurveyCheckerService.this)
                                       .setTitle("Urgent Response Alert")
                                       .setCancelable(false)
                                       .setMessage("NewSurvey from" + messages.get(temp).senderName + "\n topic :" + messages.get(temp).title).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                           public void onClick(DialogInterface dialog, int which) {
                                               // continue with delete
                                                 Intent intent2 = new Intent(getBaseContext(), Questionaire.class);
                                               intent2.putExtra("firstKeyName","" + messages.get(temp).id);
                                               intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                               Intent intent = new Intent(NewSurveyCheckerService.this, NewSurveyCheckerService.class);

                                               stopService(intent);

                                               startActivity(intent2);


                                           }
                                       })
                                       .create();

                               alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                               if (alertDialog.isShowing()) {
                                   Log.d("hai", "pritntoast" + temp + isServiceRunning());
                               } else {
                                   alertDialog.show();

                               }
                           }
                       });

                   }
               }
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String sendingUrl =ipclass.url+"/api/getquestionnaire/foruser/"+sessions.getuserId();

            String inputLine = null;
            String jsonResponse = "";
            try {
                URL url = new URL(sendingUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                while((inputLine = reader.readLine()) != null)
                {
                    jsonResponse += inputLine;
                };
                Log.d("hai",isServiceRunning()+"running");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                JSONArray jsonMessageList = new JSONArray(jsonResponse);
                for(int i=0;i<jsonMessageList.length();i++)
                {
                    JSONObject row = jsonMessageList.getJSONObject(i);
                    messages.add(new Message(row.getInt("idquestionnaire"),row.getString("username"),row.getString("title"), row.getString("note"),row.getString("timestamppost"),row.getInt("forcedResponse"),row.getInt("anonymous")));
                    //Message message = new Message(1,"CRESPOTER","TESTTT", "TESTNOTE","DATETIME");
                    //messages.add(message);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.survey.iiits.survey_iiits.NewSurveyCheckerService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

