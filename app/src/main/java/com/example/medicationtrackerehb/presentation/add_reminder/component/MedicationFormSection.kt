package com.example.medicationtrackerehb.presentation.add_reminder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.core.medicationFormPainter

@Composable
fun MedicationFormSection(
    modifier: Modifier,
    onFormChange : (MedicationForm) -> Unit,
    selectedMedicationForm: MedicationForm
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Text(text = "Type", color = Color(0xFF6D6C6C))
    Spacer(modifier = Modifier.height(8.dp))

    LazyRow(modifier = modifier) {
        items(MedicationForm.values()) {
            val formIcon = painterResource(id = medicationFormPainter(it))
            val isSelected = selectedMedicationForm == it
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        onFormChange(it)
                    }
                    .background(if (isSelected) Color(0xFFACFABD) else Color.White)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0x45B4FCEB))
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = formIcon,
                        contentDescription = "form",
                        modifier = Modifier.size(36.dp),
                        tint = Color(
                            0xFF4A85FA
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = it.name)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}