package com.example.medicationtrackerehb.presentation.add_reminder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicationtrackerehb.presentation.add_reminder.component.DateSelectSection
import com.example.medicationtrackerehb.presentation.add_reminder.component.MedicationCustomTextField
import com.example.medicationtrackerehb.presentation.add_reminder.component.MedicationFormSection
import com.example.medicationtrackerehb.presentation.add_reminder.component.TimeSection
import com.example.medicationtrackerehb.presentation.add_reminder.util.IntervalInTimes
import com.example.medicationtrackerehb.ui.theme.Blue700
import java.util.Calendar

@Composable
fun AddReminderScreen(
    navController: NavController,
    viewModel: AddReminderViewModel = hiltViewModel()
) {


    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.collectAsState()

    val context = LocalContext.current
//    val hasNotificationPermission by remember {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            mutableStateOf(
//                ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED
//            )
//        } else {
//            mutableStateOf(true)
//        }
//    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x12B0E1E7))

                .padding(paddingValue)
                .verticalScroll(rememberScrollState()),

            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(Color.Transparent)
                    .padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .align(
                            Alignment.CenterStart
                        )
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Add Reminder",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(1.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                MedicationCustomTextField(onValueChange = {
                    viewModel.onEvent(AddReminderEvents.MedicineNameChange(it))
                }, text = "Name", value = state.value.medicineName)

                Spacer(modifier = Modifier.height(16.dp))


                // Type Section

                MedicationFormSection(
                    modifier = Modifier.fillMaxWidth(),
                    onFormChange = { medicationForm ->
                        viewModel.onEvent(AddReminderEvents.MedicineFormSelect(medicationForm))

                    },
                    selectedMedicationForm = state.value.medicationForm
                )

                // end of type section

                Spacer(modifier = Modifier.height(16.dp))

                MedicationCustomTextField(
                    onValueChange = {
                        viewModel.onEvent(
                            AddReminderEvents.MedicineNumberOfDosesChange(
                                it.trim()
                            )
                        )
                    },
                    text = "Number of doses",
                    value = state.value.medicationNumberOfDoses,
                    keyboardType = KeyboardType.Decimal,
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Inventory

                MedicationCustomTextField(
                    onValueChange = {
                        viewModel.onEvent(AddReminderEvents.MedicineInventoryChange(it))
                    },
                    text = "Inventory",
                    value = state.value.medicationInventory,
                    keyboardType = KeyboardType.Decimal
                )

                // end of Inventory

                Spacer(modifier = Modifier.height(16.dp))

                // date selector

                DateSelectSection(
                    onDateStartChange = {
                        viewModel.onEvent(AddReminderEvents.StartDateSelect(it))
                    },
                    onDateEndChange = {
                        viewModel.onEvent(AddReminderEvents.EndDateSelect(it))
                    },
                    startDateValue = state.value.startDate ?: Calendar.getInstance().timeInMillis,
                    endDateValue = state.value.endDate ?: Calendar.getInstance().timeInMillis
                )

                Spacer(modifier = Modifier.height(12.dp))
                // Frequency

                Text(text = "Frequency", color = Color(0xFF6D6C6C))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    for (i in IntervalInTimes.values()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(0.4f)
                                .defaultMinSize(minHeight = 80.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .border(
                                    width = 2.dp,
                                    color = if (state.value.intervalInTimes == i) Color.Red else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.onEvent(AddReminderEvents.ChangeInterval(i))
                                },
                        ) {
                            Text(
                                text = i.interval,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .align(Alignment.Center),
                                softWrap = true,
                                textAlign = TextAlign.Center,
                                fontWeight = if (state.value.intervalInTimes == i) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                if (state.value.intervalInTimes == IntervalInTimes.EveryXHour) {
                    Spacer(modifier = Modifier.size(12.dp))
                    MedicationCustomTextField(
                        onValueChange = {
                            viewModel.onEvent(
                                AddReminderEvents.IntervalBetweenDosesChange(
                                    it.trim()
                                )
                            )
                        },
                        text = "Time between doses",
                        value = state.value.intervalBetweenDoses,
                        keyboardType = KeyboardType.Decimal
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))

                TimeSection(
                    modifier = Modifier.fillMaxWidth(),
                    timeList = state.value.times,
                    onAddTime = {
                        viewModel.onEvent(AddReminderEvents.NewTimeAdd(it))
                    },
                    onDeleteTime = {
                        viewModel.onEvent(AddReminderEvents.RemoveTime(it))
                    },
                    onEditTime = { time, index ->
                        viewModel.onEvent(AddReminderEvents.EditTime(time, index))
                    },
                    intervalInTimes = state.value.intervalInTimes
                )

                Spacer(modifier = Modifier.size(8.dp))

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Blue700),
                    onClick = {
                        viewModel.onEvent(AddReminderEvents.SaveMedicineReminder)
                        navController.popBackStack()
                    }) {
                    Text(text = "Save")
                }

            }
        }
    }
}

