package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.domain.repository.MedicationRepository

class GetReminderUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(id: Int) : NotificationEntity {
        return repository.getReminder(id)
    }

}