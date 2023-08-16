package com.example.medicationtrackerehb.presentation.add_reminder.component

import android.text.format.DateFormat
import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.medicationtrackerehb.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DateSelectSection(
    modifier: Modifier = Modifier,
    onDateStartChange: (Long) -> Unit,
    onDateEndChange: (Long) -> Unit,
    startDateValue: Long,
    endDateValue: Long,
) {

    var startDateShowPicker by remember { mutableStateOf(false) }
    var endDateShowPicker by remember { mutableStateOf(false) }

    if (startDateShowPicker) {
        CalendarDialog(
            onDateChange = onDateStartChange,
            onDismissRequest = {
                startDateShowPicker = false
            },
            selectedDate = startDateValue
        )
    }

    if (endDateShowPicker) {
        CalendarDialog(
            onDateChange = onDateEndChange,
            onDismissRequest = {
                endDateShowPicker = false
            },
            selectedDate = endDateValue,
            startDate = startDateValue
        )
    }


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        CustomDatePickerField(
            text = stringResource(R.string.start_date_dateselectionsection),
            modifier = Modifier
                .weight(1f),
            onValueChange = {},
            timestamp = startDateValue,
            onOpenDatePicker = {
                startDateShowPicker = true
            }
        )

        Spacer(modifier = Modifier.width(32.dp))

        CustomDatePickerField(
            text = stringResource(R.string.end_date_dateselectionsection),
            modifier = Modifier.weight(1f),
            onValueChange = {},
            timestamp = endDateValue,
            onOpenDatePicker = {
                endDateShowPicker = true
            }
        )
    }

}

@Composable
fun CalendarDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDateChange: (Long) -> Unit,
    selectedDate: Long? = null,
    startDate: Long? = null,
) {

    var selDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Column(
            modifier = modifier
                .wrapContentSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {

                Text(
                    text = stringResource(R.string.select_date_dateselectionsection),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = DateFormat.format("MMM d,yyyy", selectedDate ?: 0L).toString(),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
            CustomCalendarView(onDateChange = {
                selDate = it
                onDateChange(selDate)
                Log.d("time 1", "$it")

            }, selectedDate = selectedDate, startDate = startDate)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.size(8.dp))
                Button(onClick = {
                    onDateChange(selDate)
                    onDismissRequest()
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}


@Composable
fun CustomCalendarView(onDateChange: (Long) -> Unit, selectedDate: Long?, startDate: Long?) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(context).apply {
                if (startDate != null) {
                    minDate = startDate
                }
                if (selectedDate != null) {
                    date = selectedDate
                }
            }
        },
        update = { view ->
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth, 0, 0, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                onDateChange(calendar.timeInMillis)
                Log.d("Time Calendar", calendar.timeInMillis.toString())
            }
        }
    )
}

@Composable
fun CustomDatePickerField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (Long) -> Unit,
    timestamp: Long,
    onOpenDatePicker: () -> Unit
) {
    Log.d("sssss", "$timestamp")
    OutlinedTextField(
        modifier = modifier
            .clickable { onOpenDatePicker() },
        label = {
            Text(text = text, color = Color.Black)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            disabledTextColor = Color.Black,
            disabledTrailingIconColor = Color.Black
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.clickable { onOpenDatePicker() })
        },
        value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(timestamp),
        onValueChange = {
            onValueChange(it.toLong())
        },
        shape = RoundedCornerShape(16.dp),
        readOnly = false,
        enabled = false
    )

}
