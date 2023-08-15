package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import com.example.medicationtrackerehb.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow

class GetMedicationWithReminderListUseCase(
    private val repository: MedicationRepository
) {

    operator fun invoke(): Flow<List<NotificationWithMedication>> {
        return repository.getListOfMedicationsWithReminders()
    }

}