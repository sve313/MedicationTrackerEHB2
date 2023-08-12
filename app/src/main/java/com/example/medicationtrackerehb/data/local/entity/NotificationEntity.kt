package com.example.medicationtrackerehb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.medicationtrackerehb.core.enums.TakeStatus


@Entity
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val medication_id: Int,
    val time: Long,
    val takeStatus: TakeStatus = TakeStatus.Pending
)