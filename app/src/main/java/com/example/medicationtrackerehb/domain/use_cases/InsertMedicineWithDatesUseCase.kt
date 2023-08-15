package com.example.medicationtrackerehb.domain.use_cases

import com.example.medicationtrackerehb.core.Constants.HOUR_IN_MILLIS
import com.example.medicationtrackerehb.core.enums.TakeStatus
import com.example.medicationtrackerehb.data.local.entity.MedicationEntity
import com.example.medicationtrackerehb.data.local.entity.NotificationEntity
import com.example.medicationtrackerehb.domain.repository.MedicationRepository
import com.example.medicationtrackerehb.presentation.add_reminder.util.IntervalInTimes
import java.util.Calendar

class InsertMedicineWithDatesUseCase(
    private val repository: MedicationRepository
) {

    suspend operator fun invoke(
        medicationEntity: MedicationEntity,
        times: List<Long>,
        intervalInTimes: IntervalInTimes
    ) {
        val medicationId = repository.insertMedication(medicationEntity)
        if (intervalInTimes != IntervalInTimes.AsNeeded) {
            repository.insertReminders(
                getListOfReminderDates(
                    id = medicationId,
                    times = times,
                    endDate = medicationEntity.endDate.setToStartOfDay(),
                    interval = medicationEntity.intervalBetweenDoses,
                    startDate = medicationEntity.startDate.setToStartOfDay(),
                )
            )
        }
    }

    private fun Long.setToStartOfDay() : Long{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(year, month, dayOfMonth,0,0,0)
        return calendar.timeInMillis
    }
    private fun getListOfReminderDates(
        id: Int,
        times: List<Long>,
        endDate: Long,
        interval: Int,
        startDate: Long
    ): MutableList<NotificationEntity> {
        val reminderList = mutableListOf<NotificationEntity>()
        val intervalBetweenDosesInMillis = interval * HOUR_IN_MILLIS
        for (time in times) {
            var newTime = time + startDate
            while (newTime <= endDate) {
                reminderList.add(
                    NotificationEntity(
                        medication_id = id,
                        time = newTime,
                        takeStatus = if (newTime < Calendar.getInstance().timeInMillis) {
                            TakeStatus.Taken
                        } else {
                            TakeStatus.Pending
                        }
                    )
                )
                newTime += intervalBetweenDosesInMillis
            }
        }
        return reminderList
    }

}