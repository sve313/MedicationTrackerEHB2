package com.example.medicationtrackerehb.domain.model

import com.example.medicationtrackerehb.core.enums.TakeStatus

data class Reminder(
    val id : Int = 0,
    val medication_id : Int,
    val time : Long,
    val takeStatus : TakeStatus = TakeStatus.Pending
)
