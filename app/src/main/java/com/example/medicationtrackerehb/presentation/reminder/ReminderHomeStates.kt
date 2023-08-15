package com.example.medicationtrackerehb.presentation.reminder

import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

data class ReminderHomeStates(
    val reminderList : List<NotificationWithMedication> = emptyList(),
    val selectedDay : Long = 0L
)
