package com.example.medicationtrackerehb.di

import android.app.Application
import androidx.room.Room
import com.example.medicationtrackerehb.data.local.MedicationDao
import com.example.medicationtrackerehb.data.local.MedicationDatabase
import com.example.medicationtrackerehb.data.local.MedicationDatabase.Companion.MEDICATION_DATABASE
import com.example.medicationtrackerehb.data.repository.MedicationRepositoryImpl
import com.example.medicationtrackerehb.domain.repository.MedicationRepository
import com.example.medicationtrackerehb.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MedicationDatabase {
        return Room.databaseBuilder(
            app,
            MedicationDatabase::class.java,
            MEDICATION_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: MedicationDatabase): MedicationDao {
        return db.MedicationDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: MedicationDao): MedicationRepository {
        return MedicationRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: MedicationRepository): MedicationUseCases {
        return MedicationUseCases(
            insertMedicineWithDatesUseCase = InsertMedicineWithDatesUseCase(repository),
            getReminderListUseCase = GetReminderListUseCase(repository),
            getMedicationWithReminderListUseCase = GetMedicationWithReminderListUseCase(repository),
            updateReminderUseCase = UpdateReminderUseCase(repository),
            getReminderUseCase = GetReminderUseCase(repository),
            medicationWithReminderListUseCase = MedicationWithReminderListUseCase(repository),
            getDrugWithReminderDetailsUseCase = GetDrugWithReminderDetailsUseCase(repository),
            getMedicationListUseCase = GetMedicationListUseCase(repository),
            deleteMedicineWithDatesUseCase = DeleteMedicineWithDatesUseCase(repository),
            updateMedicationNotificationUseCase = UpdateMedicationNotificationUseCase(repository),
            getMedicationUseCase = GetMedicationUseCase(repository)
        )
    }



}