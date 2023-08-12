package com.example.medicationtrackerehb.data.local

import androidx.room.*
import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import kotlinx.coroutines.flow.Flow

const val QUERY_FOR_REMINDER_WITH_MEDICATION =
    "SELECT MedicationEntity.id as drugId, MedicationEntity.name as drugName, MedicationEntity.inventory as inventory," +
            "NotificationEntity.id as reminderId, NotificationEntity.takeStatus as takeStatus,  " +
            "NotificationEntity.time as time , MedicationEntity.form as medicationForm, " +
            "MedicationEntity.isNotificationOn as isNotificationOn " +
            "FROM NotificationEntity JOIN MedicationEntity " +
            "ON MedicationEntity.id = NotificationEntity.medication_id"

@Dao
interface MedicationDao {

    //medication start
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedication(medication: MedicationEntity): Long

    @Query("UPDATE medicationentity set isNotificationOn = :isNotificationOn WHERE id = :drugId")
    suspend fun updateMedication(drugId: Int, isNotificationOn: Boolean)

    @Query("DELETE FROM medicationentity WHERE id = :medicationId")
    suspend fun removeMedication(medicationId: Int)

    @Query("DELETE FROM notificationentity WHERE medication_id = :medicationId")
    suspend fun removeMedicationReminders(medicationId: Int)

    @Query("SELECT * FROM MedicationEntity")
    fun getMedicationsList(): Flow<List<MedicationEntity>>

    @Query("SELECT * FROM MedicationEntity WHERE id = :id")
    suspend fun getMedicationInfo(id: Int): MedicationEntity

    // notification start
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateNotification(reminders: List<NotificationEntity>)

    @Query("SELECT * FROM NotificationEntity WHERE id = :id")
    suspend fun getNotification(id: Int): NotificationEntity

    @Update
    suspend fun updateNotification(reminder: NotificationEntity)

    @Query("SELECT * FROM NotificationEntity")
    fun getNotificationList(): Flow<List<NotificationEntity>>

    @Query(
        QUERY_FOR_REMINDER_WITH_MEDICATION
    )
    fun getMedicaitonWithNotificationList(): Flow<List<NotificationWithMedication>>

    @Query(
        "SELECT MedicationEntity.id as drugId, MedicationEntity.name as drugName, MedicationEntity.inventory as inventory," +
                "NotificationEntity.id as reminderId, NotificationEntity.takeStatus as takeStatus,  " +
                "NotificationEntity.time as time , MedicationEntity.form as medicationForm, " +
                "MedicationEntity.isNotificationOn as isNotificationOn " +
                "FROM NotificationEntity JOIN MedicationEntity ON NotificationEntity.medication_id = MedicationEntity.id " +
                "WHERE MedicationEntity.id = :id"
    )
    fun getMedicationWithNotification(id: Int): List<NotificationWithMedication>

    @Query(
        QUERY_FOR_REMINDER_WITH_MEDICATION
    )
    suspend fun getMedicationWithNotificationListS(): List<NotificationWithMedication>


}