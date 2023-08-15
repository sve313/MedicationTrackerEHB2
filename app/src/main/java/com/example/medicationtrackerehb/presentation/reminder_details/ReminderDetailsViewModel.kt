package com.example.medicationtrackerehb.presentation.reminder_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.medicationtrackerehb.PillApplication
import com.example.medicationtrackerehb.core.enums.TakeStatus
import com.example.medicationtrackerehb.core.nav_graph.DETAIL_ARGUMENT_REMINDER
import com.example.medicationtrackerehb.core.removeAlarm
import com.example.medicationtrackerehb.core.setAlarm
import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import com.example.medicationtrackerehb.domain.use_cases.MedicationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReminderDetailsViewModel @Inject constructor(
    private val useCases: MedicationUseCases,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(ReminderDetailsStates())
    val state: StateFlow<ReminderDetailsStates> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    lateinit var medication: MedicationEntity

    private val context = getApplication<PillApplication>().applicationContext

    init {
        savedStateHandle.get<Int>(DETAIL_ARGUMENT_REMINDER).let { reminderId ->
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("id passed ", reminderId.toString())

                medication = useCases.getMedicationUseCase(reminderId!!)
                val reminders: List<NotificationWithMedication> = try {
                    useCases.getDrugWithReminderDetailsUseCase(reminderId)
                        .apply {
                            println(this)
                        }
                        .filter { it.takeStatus == TakeStatus.Taken }
                        .apply {
                            println(this)
                        }
                } catch (e: Exception) {
                    Log.d("e", e.message.toString())
                    emptyList()
                }

                val takenMedication = reminders.count { it.takeStatus == TakeStatus.Taken }
                val missedMedication = reminders.count { it.takeStatus == TakeStatus.Missed }
                val skippedMedication = reminders.count { it.takeStatus == TakeStatus.Skipped }
                _state.update { states ->
                    states.copy(
                        medicineName = medication.name,
                        medicineForm = medication.form,
                        takenMedication = takenMedication,
                        missedMedication = missedMedication,
                        skippedMedication = skippedMedication,
                        inventoryMedication = medication.inventory.minus(takenMedication) ?: 0,
                        lastThreeMeds = reminders,
                        isNotificationOn = medication.isNotificationOn
                    )
                }
            }
        }
    }

    fun onEvent(event: ReminderDetailsEvents) {
        when (event) {
            is ReminderDetailsEvents.DeleteMedicationWithReminder -> {
                viewModelScope.launch {
                    useCases.deleteMedicineWithDatesUseCase(medication.id)
                    _eventFlow.emit(UIEvent.ReminderDeleted)
                }
            }

            is ReminderDetailsEvents.TurnReminderNotification -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(
                            isNotificationOn = !it.isNotificationOn
                        )
                    }
                    val reminders = useCases.medicationWithReminderListUseCase()
                    reminders.filter { it.time >= Calendar.getInstance().timeInMillis && it.medicationId == medication.id }
                        .forEach {
                            if (state.value.isNotificationOn) {
                                setAlarm(it, context)
                                _eventFlow.emit(UIEvent.NotificationStateChange(message = "Notification On"))
                            } else {
                                removeAlarm(it, context)
                                _eventFlow.emit(UIEvent.NotificationStateChange(message = "Notification Off"))

                            }

                        }
                    useCases.updateMedicationNotificationUseCase(
                        drugId = medication.id,
                        isNotificationOn = state.value.isNotificationOn
                    )
                    Log.d("done", "done ${state.value.isNotificationOn}")
                }
            }
        }
    }

    sealed interface UIEvent {
        object ReminderDeleted : UIEvent
        data class NotificationStateChange(val message: String) : UIEvent
    }

}

fun List<NotificationWithMedication>.takeLastPills(n: Int): List<NotificationWithMedication> {
    val newList = mutableListOf<NotificationWithMedication>()
    this.forEach {
        if (it.takeStatus == TakeStatus.Taken) {
            newList.add(it)
        }
    }
    return newList.toList().takeLast(n)
}