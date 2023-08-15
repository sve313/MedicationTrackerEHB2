package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.domain.repository.MedicationRepository

class GetMedicationUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(id: Int) : MedicationEntity {
        return repository.getMedication(id)
    }

}