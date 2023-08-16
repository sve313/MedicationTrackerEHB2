package com.example.medicationtrackerehb.presentation.reminder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.core.enums.TakeStatus
import com.example.medicationtrackerehb.core.medicationFormPainter
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReminderItems(
    modifier: Modifier = Modifier,
    remindersList: List<NotificationWithMedication>,
    selectedReminder: (NotificationWithMedication) -> Unit
) {

    val context = LocalContext.current
    LazyColumn(
        modifier = modifier

    ) {
        items(remindersList) { reminder ->
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(Color(0xFFffffff), shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        selectedReminder(reminder)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .background(Color(0xFCBBE8F1))
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = medicationFormPainter(
                                reminder.medicationForm
                            )
                        ), contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = reminder.medicationName,
                        style = MaterialTheme.typography.h6
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                when (reminder.takeStatus) {
                                    TakeStatus.Pending -> Color(0xffdddddd)
                                    TakeStatus.Taken -> Color(0x5C45F87F)
                                    TakeStatus.Skipped -> Color.Red
                                    TakeStatus.Missed -> Color.Red
                                }, shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text(when (reminder.takeStatus){
                            TakeStatus.Missed -> stringResource(R.string.missed_takestatus)
                            TakeStatus.Pending -> stringResource(R.string.pending_takestatus)
                            TakeStatus.Skipped -> stringResource(R.string.skipped_takestatus)
                            TakeStatus.Taken -> stringResource(R.string.taken_takestatus)
                        })
                        }
                    }


                }
                //Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(
                            reminder.time
                        ),
                        modifier = Modifier
                            .background(
                                color = Color(0xFF98D9FC),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

            }
            //Spacer(modifier = Modifier.size(8.dp))
        }

}