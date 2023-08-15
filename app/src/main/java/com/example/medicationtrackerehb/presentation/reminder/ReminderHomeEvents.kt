package com.example.medicationtrackerehb.presentation.reminder

import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

sealed interface ReminderHomeEvents {
    data class OnDayChange(val startOfDay: Long) : ReminderHomeEvents
    data class ReminderDetails(val notificationWithMedication: NotificationWithMedication) : ReminderHomeEvents
    data class OnTakeRequest(val notificationWithMedication: NotificationWithMedication) : ReminderHomeEvents
    data class OnSkipRequest(val notificationWithMedication: NotificationWithMedication) : ReminderHomeEvents
}