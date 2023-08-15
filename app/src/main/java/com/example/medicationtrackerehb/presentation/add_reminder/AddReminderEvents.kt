package com.example.medicationtrackerehb.presentation.add_reminder

import android.content.Context
import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.presentation.add_reminder.util.IntervalInTimes

sealed class AddReminderEvents {
    data class MedicineNameChange(val medicineName: String) : AddReminderEvents()
    data class MedicineNumberOfDosesChange(val numberOfDoses: String) : AddReminderEvents()
    data class MedicineInventoryChange(val inventory: String) : AddReminderEvents()
    data class IntervalBetweenDosesChange(val hours: String) : AddReminderEvents()
    data class MedicineFormSelect(val medicationForm: MedicationForm) : AddReminderEvents()
    data class StartDateSelect(val date: Long) : AddReminderEvents()
    data class EndDateSelect(val date: Long) : AddReminderEvents()
    data class NewTimeAdd(val date: Long) : AddReminderEvents()
    data class RemoveTime(val index: Int) : AddReminderEvents()
    data class EditTime(val time: Long, val index: Int) : AddReminderEvents()
    data class ChangeInterval(val intervalInTimes: IntervalInTimes) : AddReminderEvents()
    object SaveMedicineReminder : AddReminderEvents()
}
