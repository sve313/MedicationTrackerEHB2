package com.example.medicationtrackerehb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.core.enums.MedicineStatus
import com.example.medicationtrackerehb.core.enums.TakeStatus
import com.example.medicationtrackerehb.data.local.MedicationDao
import com.example.medicationtrackerehb.data.local.MedicationDatabase
import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MedicationDaoTest {
    private lateinit var medicationDao: MedicationDao
    private lateinit var medicationDatabase: MedicationDatabase
    private val notificationEntity = NotificationEntity(1,1,1692228868106,TakeStatus.Pending)
    private val medicationEntity = MedicationEntity(1,"test",MedicationForm.Tablet,1,0,1,MedicineStatus.Active,1692228868106,1892228868106,true)

    @Before
    fun createDB(){
        val context: Context = ApplicationProvider.getApplicationContext()

        medicationDatabase = Room.inMemoryDatabaseBuilder(context,MedicationDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        medicationDao = medicationDatabase.MedicationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        medicationDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_MedicationIntoDB() = runBlocking {
        addMedicationToDb()
        val entity = medicationDao.getMedicationInfo(1)
        assertEquals(entity,medicationEntity)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_NotificationIntoDB() = runBlocking {
        addNotificationToDb()
        val entity = medicationDao.getNotification(1)
        assertEquals(entity,notificationEntity)
    }

    @Test
    @Throws(Exception::class)
    fun doaDelete_MedicationInDb() = runBlocking{
        addMedicationToDb()
        medicationDao.removeMedication(1)
        val list = medicationDao.getMedicationsList().first()
        assertTrue(list.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateMedicationInDB() = runBlocking {
        addMedicationToDb()
        medicationDao.updateMedication(1,false)

        val entity = medicationDao.getMedicationInfo(1)
        val entity2 = MedicationEntity(1,"test",MedicationForm.Tablet,
            1,0,1,MedicineStatus.Active,
            1692228868106,1892228868106,false)

        assertEquals(entity,entity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateNotificationInDB() = runBlocking {
        addNotificationToDb()
        val entity = NotificationEntity(1,2,1692228868106,TakeStatus.Pending)
        medicationDao.updateNotification(entity)
        val entity2 = medicationDao.getNotification(1)

        assertEquals(entity,entity2)
    }


    private suspend fun addMedicationToDb(){
        medicationDao.insertMedication(medicationEntity)
    }
    private suspend fun addNotificationToDb(){
        medicationDao.insertOrUpdateNotification(listOf(notificationEntity))
    }


}