package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.domain.repository.MedicationRepository

class UpdateReminderUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(reminder: NotificationEntity) {
        repository.updateReminder(reminder)
    }

}