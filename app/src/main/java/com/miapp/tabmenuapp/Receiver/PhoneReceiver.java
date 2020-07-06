package com.miapp.tabmenuapp.Receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.miapp.tabmenuapp.R;

import java.util.Calendar;

public class PhoneReceiver extends BroadcastReceiver {
    static boolean ring=false;
    static boolean callReceived=false;
    public static String CHANNEL_ID = "TEST";
    public static String ACTION_SNOOZE = "Send_Message";
    public static String EXTRA_NOTIFICATION_ID = "extraNotification";
    public static int notificationID = 100;


    @Override
    public void onReceive(Context mContext, Intent intent)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Canal";
            String descripcion = "Canal notificacion phone";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descripcion);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        // Get the current Phone State
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state==null)
            return;

        // If phone state "Rininging"
        Bundle extras = intent.getExtras();
        if(extras != null){
            String callerPhoneNumber =extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);;
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                ring =true;
                // Get the Caller's Phone Number
                Log.d("caller phone numbah", ""+extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
            }
            // If incoming call is received
            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                callReceived=true;
            }
            // If phone is Idle
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                // If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
                if(ring==true&&callReceived==false)
                {
                    Toast.makeText(mContext, "It was A MISSED CALL from : "+callerPhoneNumber, Toast.LENGTH_LONG).show();
                    if(callerPhoneNumber != null){
                        long[] x = new long[]{Long.parseLong("100"), Long.parseLong("250"), Long.parseLong("100"), Long.parseLong("500")};

                        //Indicación de la acción del botón en la notificación
                        Intent snoozeIntent = new Intent(mContext.getApplicationContext(), MyBroadcastReceiver.class);
                        snoozeIntent.setAction(ACTION_SNOOZE);
                        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
                        snoozeIntent.putExtra("data", "Hola mundo");

                        PendingIntent snoozePendingIntent =
                                PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, snoozeIntent, 0);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext.getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("LLAMADA PERDIDA")
                                .setContentText("Has pedido una llamada de "+callerPhoneNumber)
                                .setVibrate(x)
                                .setAutoCancel(true)
                                .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                                .addAction(R.drawable.ic_launcher_background, "Acción Botón", snoozePendingIntent);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext.getApplicationContext());

                        notificationManager.notify(notificationID, builder.build());
                    }
                }
            }
        }


    }





}
