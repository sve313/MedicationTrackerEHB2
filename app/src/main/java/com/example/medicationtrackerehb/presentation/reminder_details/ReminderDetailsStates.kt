package com.example.medicationtrackerehb.presentation.reminder_details

import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

data class ReminderDetailsStates(
    val medicineName : String = "",
    val medicineForm: MedicationForm = MedicationForm.Tablet,
    val takenMedication : Int = 0,
    val inventoryMedication : Int = 0,
    val missedMedication : Int = 0,
    val skippedMedication : Int = 0,
    val isNotificationOn : Boolean = true,
    val lastThreeMeds : List<NotificationWithMedication> = emptyList(),
)