package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.domain.repository.MedicationRepository

class UpdateMedicationNotificationUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(drugId : Int, isNotificationOn : Boolean) {
        repository.updateMedication(drugId, isNotificationOn)
    }

}