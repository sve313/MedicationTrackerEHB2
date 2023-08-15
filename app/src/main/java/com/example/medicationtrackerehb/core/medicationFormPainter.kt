package com.example.medicationtrackerehb.core

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.medicationtrackerehb.R
import com.example.medicationtrackerehb.core.enums.MedicationForm


fun medicationFormPainter(medicationForm: MedicationForm) : Int {
    return when (medicationForm) {
        MedicationForm.Tablet ->  R.drawable.ic_tablet
        MedicationForm.Capsule -> R.drawable.ic_capsule
        MedicationForm.Drops ->  R.drawable.ic_drops
        MedicationForm.Cream -> R.drawable.ic_cream
        MedicationForm.Solution -> R.drawable.ic_de
        MedicationForm.Injection ->  R.drawable.ic_injection
        MedicationForm.Inhalation ->  R.drawable.ic_injection
    }
}