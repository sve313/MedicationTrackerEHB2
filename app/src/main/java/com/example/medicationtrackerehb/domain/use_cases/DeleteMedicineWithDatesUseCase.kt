package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.domain.repository.MedicationRepository

class DeleteMedicineWithDatesUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(drugId: Int) {
        repository.removeMedication(drugId)
    }

}