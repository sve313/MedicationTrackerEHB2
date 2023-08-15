package com.example.medicationtrackerehb.core

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.medicationtrackerehb.MainActivity
import com.example.medicationtrackerehb.data.broadcast_reciver.NotificationReceiver
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

fun setAlarm(drugReminder: NotificationWithMedication, context: Context?) {

    // creating alarmManager instance
    val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // adding intent and pending intent to go to AlarmReceiver Class in future
    val intent = Intent(context, NotificationReceiver::class.java)
    intent.putExtra("drug_reminder", drugReminder)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        drugReminder.reminderId,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    // when using setAlarmClock() it displays a notification until alarm rings and when pressed it takes us to mainActivity
    val mainScreenIntent = Intent(context, MainActivity::class.java)
    val mainPendingIntent = PendingIntent.getActivity(
        context,
        drugReminder.reminderId,
        mainScreenIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    // creating clockInfo instance
    val clockInfo = AlarmManager.AlarmClockInfo(drugReminder.time, mainPendingIntent)

    // setting the alarm
    alarmManager.setAlarmClock(clockInfo, pendingIntent)

}

 fun removeAlarm(drugReminder: NotificationWithMedication, context: Context?){
    val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    // It is not necessary to add putExtra
    intent.putExtra("drug_reminder", drugReminder)
    val pendingIntent = PendingIntent.getBroadcast(context, drugReminder.reminderId, intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.cancel(pendingIntent)
}
