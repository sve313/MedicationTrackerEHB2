package com.example.medicationtrackerehb.presentation.add_reminder

import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import com.example.medicationtrackerehb.presentation.add_reminder.util.IntervalInTimes

data class AddReminderStates(
    val medicineName: String = "",
    val medicationInventory: String = "",
    val medicationNumberOfDoses: String = "",
    val medicationForm: MedicationForm = MedicationForm.Capsule,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val intervalBetweenDoses : String = "",
    val times : List<Long> = listOf(),
    val drugReminders : List<NotificationWithMedication> = listOf(),
    val intervalInTimes: IntervalInTimes = IntervalInTimes.EveryXHour,
)
