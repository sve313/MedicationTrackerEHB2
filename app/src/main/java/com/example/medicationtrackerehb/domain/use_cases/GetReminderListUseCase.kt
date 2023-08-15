package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.domain.model.Reminder
import com.example.medicationtrackerehb.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReminderListUseCase(
    private val repository: MedicationRepository
) {

    operator fun invoke(): Flow<List<Reminder>> {
        return repository.getListOfReminders().map {
            it.map {
                    notificationEntity -> notificationEntity.toNotification() }
        }


    }

}