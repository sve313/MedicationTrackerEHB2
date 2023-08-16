package com.example.medicationtrackerehb.core

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.medicationtrackerehb.MainActivity
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

fun NotificationManager.sendNotification(messageBody: String, context: Context, reminder: NotificationWithMedication) {

    val clickNotificationIntent = Intent(context, MainActivity::class.java)
    clickNotificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context, 0, clickNotificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )


    val builder = NotificationCompat.Builder(
        context,
        "reminder_channel"
    )
        .setSmallIcon(R.drawable.ic_tablet)
        .setContentTitle(
            context.getString(
                R.string.get_your_medication_notification_text,
                reminder.medicationName
            ))
        .setContentText(messageBody)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(reminder.reminderId, builder.build())
}