package com.example.medicationtrackerehb.presentation.reminder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DaySectionCard(
    modifier: Modifier = Modifier,
    onDayChange : (Long) -> Unit,
    selectedDay : Long,
    listOfDays: List<Long>
) {
    val interactionSource = remember { MutableInteractionSource() }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items(listOfDays) { startOfDay ->
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.11f)
                    .width(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        onDayChange(startOfDay)
                    }
                    .background(
                        if (startOfDay == selectedDay) Color(0xB449B5FD) else Color(
                            0xFFF8F4F2
                        )
                    )
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = SimpleDateFormat("EEE", Locale.getDefault()).format(
                        startOfDay
                    ),
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = SimpleDateFormat("dd", Locale.getDefault()).format(
                        startOfDay
                    ),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}