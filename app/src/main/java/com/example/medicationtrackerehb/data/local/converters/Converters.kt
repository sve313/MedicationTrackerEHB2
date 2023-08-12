package com.example.medicationtrackerehb.data.local.converters
/*
based on
* https://adrianhall.github.io/android/2018/08/08/converting-types-with-room-and-kotlin/
* */
import androidx.room.TypeConverter
import com.example.medicationtrackerehb.core.enums.MedicationForm
import com.example.medicationtrackerehb.core.enums.MedicineStatus
import com.example.medicationtrackerehb.core.enums.TakeStatus

class Converters {

    @TypeConverter
    fun fromMedicineStatus(medicineStatus: MedicineStatus) : Int {
        return medicineStatus.ordinal
    }

    @TypeConverter
    fun toMedicineStatus(position: Int) : MedicineStatus{
        return enumValues<MedicineStatus>()[position]
    }

    @TypeConverter
    fun fromMedicationForm(medicationForm: MedicationForm) : Int {
        return medicationForm.ordinal
    }

    @TypeConverter
    fun toMedicationForm(position: Int) : MedicationForm {
        return enumValues<MedicationForm>()[position]
    }

    @TypeConverter
    fun fromTakeStatus(takeStatus: TakeStatus) : Int {
        return takeStatus.ordinal
    }

    @TypeConverter
    fun toTakeStatus(position: Int) : TakeStatus{
        return enumValues<TakeStatus>()[position]
    }

}