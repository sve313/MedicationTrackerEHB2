package com.example.medicationtrackerehb.data.local.entity

import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.core.enums.TakeStatus
import java.io.Serializable

data class NotificationWithMedication(
    val drugId : Int,
    val reminderId: Int,
    val drugName : String,
    val takeStatus : TakeStatus,
    val medicationForm: MedicationForm,
    val time : Long,
    val inventory : Int,
    val isNotificationOn: Boolean,
) : Serializable
