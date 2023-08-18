package com.example.medicationtrackerehb.presentation.add_reminder.component

import android.os.Build
import android.util.Log
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.presentation.add_reminder.util.IntervalInTimes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TimeSection(
    modifier: Modifier = Modifier,
    timeList: List<Long>,
    onAddTime: (Long) -> Unit,
    onDeleteTime: (Int) -> Unit,
    onEditTime: (Long, Int) -> Unit,
    intervalInTimes: IntervalInTimes,
) {

    var timePickerShow by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var selectedTime by remember { mutableStateOf<Long?>(null) }

    if (timePickerShow) {
        CustomTimeDialog(
            onDismissRequest = { timePickerShow = false },
            onSubmit = {
                if (selectedIndex != null) {
                    onEditTime(it, selectedIndex!!)
                    Log.d("time", "$it")

                } else {
                    onAddTime(it)
                    Log.d("time", "$it")

                }
            },
            timeSelect = if (selectedIndex != null) {
                selectedTime
            } else {
                null
            }
        )
    }

    Column(
        modifier = modifier
    ) {
        if (intervalInTimes != IntervalInTimes.AsNeeded) {
            Text(text = stringResource(R.string.time_timesection))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {

                timeList.sorted().forEachIndexed { index, time ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                selectedIndex = index
                                timePickerShow = true
                                selectedTime = calendar.timeInMillis + time
                            },
                            // get time hour in milliseconds and add to start of day time in milliseconds
                            text = SimpleDateFormat(
                                "hh:mm a",
                                Locale.getDefault()
                            ).format(calendar.timeInMillis + time)
                        )

                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .border(width = 2.dp, color = Color.Red, shape = CircleShape)
                                .clickable {
                                    onDeleteTime(index)
                                }
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }

                if ((intervalInTimes == IntervalInTimes.EveryXHour && timeList.isEmpty()) || intervalInTimes == IntervalInTimes.SpecificHourOfDay) {
                    Button(
                        onClick = {
                            timePickerShow = true
                            selectedIndex = null
                            selectedTime = null
                        },

                        modifier = Modifier
                            .align(Alignment.Start)
                            .wrapContentSize()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(text = stringResource(R.string.add_time_timesection))
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTimeDialog(
    onDismissRequest: () -> Unit,
    timeSelect: Long? = null,
    onSubmit: (Long) -> Unit
) {

    var timeSelected by remember {
        mutableStateOf(1L)
    }

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
        ) {
            CustomTimeView(selectedTime = timeSelect) {
                timeSelected = it
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                OutlinedButton(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.size(8.dp))
                Button(onClick = {
                    onSubmit(timeSelected)
                    onDismissRequest()
                }) {
                    Text(text = "OK")
                }
            }
        }
    }

}


@Composable
fun CustomTimeView(
    selectedTime: Long? = null,
    onTimeChange: (Long) -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            TimePicker(context)
        },
        update = { view ->
            if(Build.VERSION.SDK_INT<=23){
                val calendar = Calendar.getInstance()
                if (selectedTime != null) {
                    calendar.timeInMillis = selectedTime
                    view.currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                    view.currentMinute = calendar.get(Calendar.MINUTE)
                }
                view.currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                view.currentMinute = calendar.get(Calendar.MINUTE)
                onTimeChange((view.currentHour * 3600000L) + (view.currentMinute * 60000L))

                view.setIs24HourView(false)

                view.setOnTimeChangedListener { timePicker, _, _ ->
                    val timeInMilliSec = (timePicker.currentHour * 3600000L) + (timePicker.currentMinute * 60000L)
                    onTimeChange(timeInMilliSec)
            }
            }else{
                val calendar = Calendar.getInstance()
                if (selectedTime != null) {
                    calendar.timeInMillis = selectedTime
                    view.hour = calendar.get(Calendar.HOUR_OF_DAY)
                    view.minute = calendar.get(Calendar.MINUTE)
                }
                view.hour = calendar.get(Calendar.HOUR_OF_DAY)
                view.minute = calendar.get(Calendar.MINUTE)
                onTimeChange((view.hour * 3600000L) + (view.minute * 60000L))

                view.setIs24HourView(false)

                view.setOnTimeChangedListener { timePicker, _, _ ->
                    val timeInMilliSec = (timePicker.hour * 3600000L) + (timePicker.minute * 60000L)
                    onTimeChange(timeInMilliSec)
            }
            }

        }
    )
}

@Preview
@Composable
fun TimePickPrev() {
//    CustomTimeDialog({}, {}, null)
}
