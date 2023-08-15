package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import com.example.medicationtrackerehb.domain.repository.MedicationRepository


class GetDrugWithReminderDetailsUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(id: Int): List<NotificationWithMedication> {
        return repository.medicationsWithReminders(id)
    }

}