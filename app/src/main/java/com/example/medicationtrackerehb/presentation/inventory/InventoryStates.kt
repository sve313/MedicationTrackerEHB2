package com.example.medicationtrackerehb.presentation.inventory

import com.example.medicationtrackerehb.domain.model.Medication

data class InventoryStates(
    val medicationsList: List<Medication> = emptyList()
)
