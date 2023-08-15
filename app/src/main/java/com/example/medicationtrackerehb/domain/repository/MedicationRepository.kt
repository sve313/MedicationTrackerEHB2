package com.example.medicationtrackerehb.domain.repository

import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {

    suspend fun insertMedication(medication: MedicationEntity): Int

    suspend fun updateMedication(drugId: Int, isNotificationOn: Boolean)

    suspend fun removeMedication(drugId: Int)

    suspend fun insertReminders(reminders: List<NotificationEntity>)

    fun getListOfReminders(): Flow<List<NotificationEntity>>

    fun getListOfMedications(): Flow<List<MedicationEntity>>

    fun getListOfMedicationsWithReminders(): Flow<List<NotificationWithMedication>>

    suspend fun listOfMedicationsWithReminders(): List<NotificationWithMedication>

    suspend fun medicationsWithReminders(id: Int): List<NotificationWithMedication>

    suspend fun getReminder(id: Int): NotificationEntity

    suspend fun getMedication(id: Int): MedicationEntity

    suspend fun updateReminder(reminder: NotificationEntity)
}