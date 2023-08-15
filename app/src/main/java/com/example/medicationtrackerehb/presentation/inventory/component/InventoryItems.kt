package com.example.medicationtrackerehb.presentation.inventory.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.medicationtrackerehb.core.enums.MedicineStatus
import com.example.medicationtrackerehb.core.medicationFormPainter
import com.example.medicationtrackerehb.domain.model.Medication

@Composable
fun InventoryItems(
    modifier: Modifier = Modifier,
    medicationList: List<Medication>,
    selectedReminder: (Medication) -> Unit
) {

    LazyColumn(
        modifier = modifier

    ) {
        items(medicationList) { medication ->
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(Color(0xFFffffff), shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        selectedReminder(medication)
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
                                medication.form
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
                        text = medication.name,
                        style = MaterialTheme.typography.h6
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                when (medication.status) {
                                    MedicineStatus.Active -> Color.Green
                                    MedicineStatus.Pending -> Color.LightGray
                                    MedicineStatus.Complete -> Color.Green
                                }, shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text(text = medication.status.name)
                    }


                }
                Spacer(modifier = Modifier.weight(1f))


            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}