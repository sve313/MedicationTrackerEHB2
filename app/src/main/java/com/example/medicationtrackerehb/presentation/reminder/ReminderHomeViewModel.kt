package com.example.medicationtrackerehb.presentation.reminder

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationtrackerehb.core.enums.TakeStatus
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.domain.use_cases.MedicationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReminderHomeViewModel @Inject constructor(
    private val useCases: MedicationUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ReminderHomeStates())
    val state: StateFlow<ReminderHomeStates> = _state

    private val _listOfDays = mutableStateListOf<Long>()
    val listOfDays: List<Long> = _listOfDays

    private val calendar = Calendar.getInstance()

    private var reminder: NotificationEntity? = null

    init {
        setCalendarToStartOfDay()
        _state.update {
            it.copy(selectedDay = calendar.timeInMillis)
        }
        daysList()
        getReminderListForSelectedDay()
        changeOldRemindersToMissed()
    }



    fun onEvent(event: ReminderHomeEvents) {
        when (event) {
            is ReminderHomeEvents.OnDayChange -> {
                _state.update {
                    it.copy(selectedDay = event.startOfDay)
                }
                Log.d("Calendar Day", event.startOfDay.toString())

                getReminderListForSelectedDay()
            }
            is ReminderHomeEvents.ReminderDetails -> {
                viewModelScope.launch {
                    reminder = useCases.getReminderUseCase(event.notificationWithMedication.reminderId)
                }
            }
            is ReminderHomeEvents.OnSkipRequest -> {
                viewModelScope.launch {
                    useCases.updateReminderUseCase(
                        reminder = reminder!!.copy(
                            takeStatus = TakeStatus.Skipped,
                        )
                    )
                }
            }
            is ReminderHomeEvents.OnTakeRequest -> {
                viewModelScope.launch {
                    useCases.updateReminderUseCase(
                        reminder = reminder!!.copy(
                            takeStatus = TakeStatus.Taken
                        )
                    )
                }
            }
        }
    }

    private fun changeOldRemindersToMissed() {
        useCases.getMedicationWithReminderListUseCase()
            .onEach { listOfReminders ->
                listOfReminders.filter { it.time <= Calendar.getInstance().timeInMillis }
                    .forEach { reminder ->
                        if (reminder.takeStatus == TakeStatus.Pending) {
                            viewModelScope.launch {
                                val reminderEntity = NotificationEntity(
                                    id = reminder.reminderId,
                                    medication_id = reminder.medicationId,
                                    time = reminder.time,
                                    takeStatus = reminder.takeStatus
                                )
                                useCases.updateReminderUseCase(
                                    reminder = reminderEntity.copy(
                                        takeStatus = TakeStatus.Missed
                                    )
                                )
                            }
                        }
                    }
            }.launchIn(viewModelScope)

    }

    private fun getReminderListForSelectedDay() {
        useCases.getMedicationWithReminderListUseCase()
            .onEach { listOfReminders ->
                _state.update {
                    it.copy(
                        reminderList = listOfReminders.filter { it.time in state.value.selectedDay..state.value.selectedDay + 84000000 }
                            .sortedBy { it.time }
                    )
                }
                Log.d("D", listOfReminders.toString())
            }.launchIn(viewModelScope)
    }

    private fun setCalendarToStartOfDay() {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        Log.d("Calendar", calendar.time.toString())
    }

    private fun daysList() {
        setCalendarToStartOfDay()
        calendar.add(Calendar.DAY_OF_YEAR, -2)
        _listOfDays.add(calendar.timeInMillis)
        for (i in 0..14) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            _listOfDays.add(calendar.timeInMillis)
        }
    }


}