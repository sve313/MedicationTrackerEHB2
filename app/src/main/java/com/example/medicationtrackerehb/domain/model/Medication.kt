package com.example.medicationtrackerehb.domain.model

import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.core.enums.MedicineStatus

data class Medication(
    val id : Int,
    val name : String,
    val form : MedicationForm,
    val intervalBetweenDoses: Int,
    val inventory: Int,
    val dosesPerTime: Int,
    val status : MedicineStatus = MedicineStatus.Active,
    val startDate : Long,
    val endDate : Long,
    val isNotificationOn : Boolean = true
)