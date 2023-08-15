package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import com.example.medicationtrackerehb.domain.repository.MedicationRepository

class MedicationWithReminderListUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(): List<NotificationWithMedication> {
        return repository.listOfMedicationsWithReminders()
    }

}