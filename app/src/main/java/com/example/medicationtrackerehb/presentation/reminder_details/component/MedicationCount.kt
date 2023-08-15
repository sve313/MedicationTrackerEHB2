package com.example.medicationtrackerehb.presentation.reminder_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun MedicationCount(
    text: String,
    value: Int,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "$value", style = MaterialTheme.typography.h5)

        Text(text = "Medication(s)", color = Color.Gray)
        Text(text = "$text", color = Color.Gray)
    }
}