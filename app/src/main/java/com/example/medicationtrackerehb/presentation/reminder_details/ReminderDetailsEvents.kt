package com.example.medicationtrackerehb.presentation.reminder_details

sealed interface ReminderDetailsEvents {
    object DeleteMedicationWithReminder : ReminderDetailsEvents

    object TurnReminderNotification : ReminderDetailsEvents
}