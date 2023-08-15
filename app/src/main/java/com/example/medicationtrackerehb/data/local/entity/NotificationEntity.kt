package com.example.medicationtrackerehb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.medicationtrackerehb.core.enums.TakeStatus
import com.example.medicationtrackerehb.domain.model.Reminder


@Entity
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medication_id: Int,
    val time: Long,
    val takeStatus: TakeStatus = TakeStatus.Pending
){
    fun toNotification(): Reminder {
        return Reminder(id, medication_id, time, takeStatus)
    }
}