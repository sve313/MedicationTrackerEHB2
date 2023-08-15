package com.example.medicationtrackerehb.presentation.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationtrackerehb.domain.use_cases.MedicationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val useCases: MedicationUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(InventoryStates())
    val state: StateFlow<InventoryStates> = _state

    init {
        useCases.getMedicationListUseCase().onEach { medications ->
            _state.update {
                it.copy(
                    medicationsList = medications
                )
            }
        }.launchIn(viewModelScope)
    }
//
//    fun onEvent(event: InventoryEvents){
//
//        when(event){}
//
//    }

}