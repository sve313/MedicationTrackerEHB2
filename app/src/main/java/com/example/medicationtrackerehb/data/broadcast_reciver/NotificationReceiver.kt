package com.example.medicationtrackerehb.data.broadcast_reciver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.medicationtrackerehb.core.sendNotification
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

class NotificationReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val drugReminder = intent?.getSerializableExtra("drug_reminder") as NotificationWithMedication

        val notificationManager = context?.let {
            ContextCompat.getSystemService(
                it,
                NotificationManager::class.java,
            ) as NotificationManager
        }

        notificationManager?.sendNotification(
            messageBody = "it's time for get ${drugReminder.medicationName} ",
            context,
            drugReminder
        )

    }

}