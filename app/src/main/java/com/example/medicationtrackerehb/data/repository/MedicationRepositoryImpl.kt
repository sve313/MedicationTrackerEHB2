package com.example.medicationtrackerehb.data.repository

import com.example.medicationtrackerehb.data.local.MedicationDao
import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import com.example.medicationtrackerehb.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow

class MedicationRepositoryImpl(
    private val dao: MedicationDao
) : MedicationRepository {
    override suspend fun insertMedication(medication: MedicationEntity): Int {
        return dao.insertMedication(medication).toInt()
    }

    override suspend fun updateMedication(drugId : Int, isNotificationOn : Boolean) {
        dao.updateMedication(drugId, isNotificationOn)
    }

    override suspend fun removeMedication(drugId: Int) {
        dao.removeMedication(drugId)
        dao.removeMedicationReminders(drugId)
    }

    override suspend fun insertReminders(reminders: List<NotificationEntity>) {
        dao.insertOrUpdateNotification(reminders)
    }

    override fun getListOfReminders(): Flow<List<NotificationEntity>> {
        return dao. getNotificationList()
    }

    override suspend fun getReminder(id: Int): NotificationEntity {
        return dao.getNotification(id)
    }

    override suspend fun getMedication(id: Int): MedicationEntity {
        return dao.getMedicationInfo(id)
    }

    override suspend fun updateReminder(reminder: NotificationEntity) {
        dao.updateNotification(reminder)
    }

    override suspend fun listOfMedicationsWithReminders(): List<NotificationWithMedication> {
        return dao.getMedicationWithNotificationListS()
    }

    override suspend fun medicationsWithReminders(id: Int): List<NotificationWithMedication> {
        return dao.getMedicationWithNotification(id)
    }

    override fun getListOfMedications(): Flow<List<MedicationEntity>> {
        return dao.getMedicationsList()
    }

    override fun getListOfMedicationsWithReminders(): Flow<List<NotificationWithMedication>> {
        return dao.getMedicaitonWithNotificationList()
    }


}