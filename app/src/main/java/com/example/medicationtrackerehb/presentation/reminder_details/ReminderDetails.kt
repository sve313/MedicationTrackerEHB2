package com.example.medicationtrackerehb.presentation.reminder_details

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.core.medicationFormPainter
import com.example.medicationtrackerehb.presentation.reminder_details.component.MedicationCount
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ReminderDetailsScreen(
    navController: NavController,
    viewModel: ReminderDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ReminderDetailsViewModel.UIEvent.NotificationStateChange -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message
                    )
                }

                is ReminderDetailsViewModel.UIEvent.ReminderDeleted -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x77B0E1E7))
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(modifier = Modifier.clickable {
                    navController.popBackStack()
                }, imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (state.isNotificationOn) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                    contentDescription = "Notification On",
                    modifier = Modifier.clickable {
                        viewModel.onEvent(ReminderDetailsEvents.TurnReminderNotification)
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(ReminderDetailsEvents.DeleteMedicationWithReminder)
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .background(Color(0xFCBBE8F1), shape = CircleShape)
                        .padding(8.dp)
                        .size(120.dp)
                        .padding(16.dp),
                    painter = painterResource(id = medicationFormPainter(state.medicineForm)),
                    contentDescription = "Medicine Icon"
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = state.medicineName,
                    style = MaterialTheme.typography.h5
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {

                    MedicationCount(text = "taken", value = state.takenMedication)
                    MedicationCount(text = "Inventory", value = state.inventoryMedication)
                    MedicationCount(text = "missed", value = state.missedMedication)
                    MedicationCount(text = "skipped", value = state.skippedMedication)

                }

                Divider()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    Text(
                        text = "History", style = MaterialTheme.typography.body2, color = Color(
                            0xFF53B0EE
                        )
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.lastThreeMeds.isNotEmpty()) {
                            LazyColumn() {
                                items(state.lastThreeMeds) {
                                    Text(
                                        text = SimpleDateFormat(
                                            "EEE, MMM d HH a",
                                            Locale.getDefault()
                                        ).format(it.time) + ", 1 medication(s) taken"
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                }
                            }
                        } else {
                            Text(text = "No ${state.medicineForm.name} taken history found")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PieChartComp(missedMedication: Int, skippedMedication: Int, takenMedication: Int) {

    val getPieChartData = listOf(
        PieChartData(statusName = "Missed", value = missedMedication),
        PieChartData(statusName = "Skipped", value = skippedMedication),
        PieChartData(statusName = "Taken", value = takenMedication),
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .size(320.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Crossfade(targetState = getPieChartData) { pieChartData ->
                AndroidView(
                    factory = { context ->
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )

                            this.description.isEnabled = false

                            this.isDrawHoleEnabled = false

                            // on below line we are enabling legend.
                            this.legend.isEnabled = true

                            // on below line we are specifying
                            // text size for our legend.
                            this.legend.textSize = 14F

                            // on below line we are specifying
                            // alignment for our legend.
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER

                            // on below line we are specifying entry label color as white.
                            this.setEntryLabelColor(resources.getColor(R.color.white))

                        }
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    update = {
                        updatePieChartWithData(it, pieChartData)
                    }
                )

            }

        }
    }

}

fun updatePieChartWithData(
    // on below line we are creating a variable
    // for pie chart and data for our list of data.
    chart: PieChart,
    data: List<PieChartData>
) {
    // on below line we are creating
    // array list for the entries.
    val entries = ArrayList<PieEntry>()

    // on below line we are running for loop for
    // passing data from list into entries list.
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value.toFloat() ?: 0.toFloat(), item.statusName ?: ""))
    }

    // on below line we are creating
    // a variable for pie data set.
    val ds = PieDataSet(entries, "")

    // on below line we are specifying color
    // int the array list from colors.
    ds.colors = arrayListOf(
        Color.Green.toArgb(),
        Color.Blue.toArgb(),
        Color.Red.toArgb(),
        Color.Yellow.toArgb(),
    )
    // on below line we are specifying position for value
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying position for value inside the slice.
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying
    // slice space between two slices.
    ds.sliceSpace = 2f

    // on below line we are specifying text color
    ds.valueTextColor = Color.White.toArgb()

    // on below line we are specifying
    // text size for value.
    ds.valueTextSize = 18f

    // on below line we are specifying type face as bold.
    ds.valueTypeface = Typeface.DEFAULT_BOLD

    // on below line we are creating
    // a variable for pie data
    val d = PieData(ds)

    // on below line we are setting this
    // pie data in chart data.
    chart.data = d

    // on below line we are
    // calling invalidate in chart.
    chart.invalidate()
}

data class PieChartData(
    var statusName: String,
    var value: Int
)
