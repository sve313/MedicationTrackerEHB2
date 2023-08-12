package com.example.medicationtrackerehb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.medicationtrackerehb.data.local.converters.Converters
import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity

@Database(entities = [NotificationEntity::class , MedicationEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MedicationDatabase : RoomDatabase(){

    abstract fun MedicationDao() : MedicationDao


    companion object {
        const val MEDICATION_DATABASE = "Medication_Database"
    }
}