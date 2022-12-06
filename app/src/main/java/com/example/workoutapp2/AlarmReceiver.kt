package com.example.workoutapp2

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val ALARM_NOTIFICATION_ID=3

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let{
            val contentIntent=Intent(context, MainActivity::class.java)
            val pendingIntent=PendingIntent.getActivity(context,0, contentIntent, PendingIntent.FLAG_IMMUTABLE)

            val message= intent?.getStringExtra("message") ?: ""

            val notification = NotificationCompat.Builder(context, App.ALERT_CHANNEL_ID)
                .setContentTitle("휴식 알람")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_favorite_24)
                .setContentIntent(pendingIntent)
                .build()


            context.getSystemService(NotificationManager::class.java).notify(ALARM_NOTIFICATION_ID, notification)
        }
        }

}