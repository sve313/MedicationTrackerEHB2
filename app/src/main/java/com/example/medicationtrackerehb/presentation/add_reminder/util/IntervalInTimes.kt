package com.example.medicationtrackerehb.presentation.add_reminder.util

import com.example.medicationtrackerehb.R

enum class IntervalInTimes(val interval : Int) {
    EveryXHour(R.string.every_x_hour_timeintervall),
    SpecificHourOfDay( R.string.specific_hours_of_day_intervaltimes),
    AsNeeded(R.string.as_needed_timeIntervall)

}
