package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.domain.model.Medication
import com.example.medicationtrackerehb.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMedicationListUseCase(
    private val repository: MedicationRepository
) {

    operator fun invoke(): Flow<List<Medication>> {
        return repository.getListOfMedications()
            .map { medicationEntityList -> medicationEntityList.map { it.toMedication() } }
    }

}