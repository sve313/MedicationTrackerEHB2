package com.example.medicationtrackerehb.domain.use_cases

data class MedicationUseCases(
    val insertMedicineWithDatesUseCase: InsertMedicineWithDatesUseCase,
    val getReminderListUseCase: GetReminderListUseCase,
    val getMedicationWithReminderListUseCase: GetMedicationWithReminderListUseCase,
    val updateReminderUseCase: UpdateReminderUseCase,
    val getReminderUseCase: GetReminderUseCase,
    val getMedicationUseCase: GetMedicationUseCase,
    val medicationWithReminderListUseCase: MedicationWithReminderListUseCase,
    val getDrugWithReminderDetailsUseCase: GetDrugWithReminderDetailsUseCase,
    val getMedicationListUseCase: GetMedicationListUseCase,
    val deleteMedicineWithDatesUseCase: DeleteMedicineWithDatesUseCase,
    val updateMedicationNotificationUseCase: UpdateMedicationNotificationUseCase
)
