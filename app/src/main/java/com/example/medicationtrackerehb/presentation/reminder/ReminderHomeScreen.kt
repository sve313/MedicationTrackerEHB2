package com.example.medicationtrackerehb.presentation.reminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.core.nav_graph.ADD_EDIT_ROUTE
import com.example.medicationtrackerehb.core.nav_graph.Screen
import com.example.medicationtrackerehb.data.local.entity.NotificationWithMedication

import com.example.medicationtrackerehb.presentation.reminder.component.DaySectionCard
import com.example.medicationtrackerehb.presentation.reminder.component.ReminderDialog
import com.example.medicationtrackerehb.presentation.reminder.component.ReminderItems
import com.example.medicationtrackerehb.ui.theme.Blue700


@Composable
fun ReminderHomeScreen(
    navController: NavController,
    viewModel: ReminderHomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val state = viewModel.state.collectAsState()
    val listOfDays = viewModel.listOfDays

    var showMedicationDialog by remember { mutableStateOf(false) }
    var medicationDialogItem by remember { mutableStateOf<NotificationWithMedication?>(null) }

    if (showMedicationDialog) {
        if (medicationDialogItem != null)
            ReminderDialog(
                modifier = Modifier.fillMaxWidth(),
                reminder = medicationDialogItem!!,
                onSkipRequest = {
                    viewModel.onEvent(ReminderHomeEvents.OnSkipRequest(medicationDialogItem!!))
                },
                onTakeRequest = {
                    viewModel.onEvent(ReminderHomeEvents.OnTakeRequest(medicationDialogItem!!))

                },
                onDismissRequest =
                {
                    showMedicationDialog = false
                    medicationDialogItem = null
                },
                onInfoClicked = {
                    navController.navigate(Screen.MedicineDetails.passId(medicationDialogItem!!.medicationId))
                }
            )
    }

    val context = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )

    Log.d("ViewModel", "$viewModel")

    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ADD_EDIT_ROUTE)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                ),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 22.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Home.route)
                        })
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.HealthAndSafety,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Inventory.route)
                        }
                    )
                }
            }

        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Blue700)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(R.string.medication_tracker_title), fontSize = 22.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 8.dp)
                ) {
                    DaySectionCard(
                        modifier = Modifier.fillMaxWidth(),
                        onDayChange = {
                            viewModel.onEvent(ReminderHomeEvents.OnDayChange(it))
                        },
                        selectedDay = state.value.selectedDay,
                        listOfDays = listOfDays
                    )
                }

                if (state.value.reminderList.isNotEmpty())
                    ReminderItems(
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .padding(8.dp),
                        remindersList = state.value.reminderList,
                        selectedReminder = { reminder ->
                            showMedicationDialog = true
                            medicationDialogItem = reminder
                            viewModel.onEvent(ReminderHomeEvents.ReminderDetails(reminder))
                        }
                    )
                else
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = stringResource(R.string.no_medication_today_reminderhomescreen), style = MaterialTheme.typography.h5)

                    }

            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//            ) {
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp)
//                        .background(Color.White)
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceAround,
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Home,
//                        contentDescription = null,
//                        modifier = Modifier.clickable {
//                            navController.navigate(Screen.Home.route)
//                        })
//                    Spacer(modifier = Modifier.size(1.dp))
//                    Icon(
//                        imageVector = Icons.Default.HealthAndSafety,
//                        contentDescription = null,
//                        modifier = Modifier.clickable {
//                            navController.navigate(Screen.Inventory.route)
//                        }
//                    )
//                }
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .background(MaterialTheme.colors.background.copy(alpha = 1f), shape = CircleShape,)
//                        .padding(15.dp)
//                        .background(MaterialTheme.colors.secondary, shape = CircleShape)
//                        .padding(8.dp)
//                        .size(32.dp)
//                        .align(Alignment.TopCenter)
//                        .clickable {
//                            navController.navigate(Screen.AddMedicine.route)
//                        })


        }
    }
}
