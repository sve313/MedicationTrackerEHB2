package com.example.medicationtrackerehb.presentation.reminder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.medicationtrackerehb.core.medicationFormPainter
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

@Composable
fun ReminderDialog(
    modifier: Modifier = Modifier,
    reminder: NotificationWithMedication,
    onDismissRequest: () -> Unit,
    onSkipRequest: (NotificationWithMedication) -> Unit,
    onTakeRequest: (NotificationWithMedication) -> Unit,
    onInfoClicked : () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight(0.7f)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info", modifier = Modifier.clickable {
                    onInfoClicked()
//                    onDismissRequest()
                })
                Spacer(modifier = Modifier.size(12.dp))
                Icon(
                    modifier = Modifier.clickable {
                        onDismissRequest()
                    },
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .background(Color(0xFCBBE8F1), CircleShape)
                        .padding(20.dp)
                        .size(70.dp),
                    painter = painterResource(id = medicationFormPainter(reminder.medicationForm)),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(text = reminder.medicationName, style = MaterialTheme.typography.body1)

            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onSkipRequest(reminder)
                            onDismissRequest()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(Color(0xFFD30C0C), shape = RoundedCornerShape(4.dp))
                            .size(40.dp)
                            .padding(2.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Skip", style = MaterialTheme.typography.body2)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onTakeRequest(reminder)
                            onDismissRequest()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF109C17), shape = RoundedCornerShape(4.dp))
                            .size(40.dp)
                            .padding(2.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Take", style = MaterialTheme.typography.body2)
                }


//                Column(
//                    modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Alarm,
//                        contentDescription = null,
//                        tint = Color.Black,
//                        modifier = Modifier
//                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
//                            .size(40.dp)
//                            .padding(2.dp)
//                    )
//                    Spacer(modifier = Modifier.size(4.dp))
//                    Text(text = "Reschedule", style = MaterialTheme.typography.body2)
//                }


            }
        }
    }
}
