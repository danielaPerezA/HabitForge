package com.example.habitforge.app.ui.gym

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth


data class WeekDay(
    val name: String,
    val isChecked: Boolean,
    val muscleGroup: String,
    val isToday: Boolean = false
)

data class MonthProgress(
    val month: String,
    val monthNumber: Int,
    val daysCount: Int,
    val goal: Int,
    val mostWorked: String
) {
    val progress: Float
        get() = if (goal > 0) (daysCount.toFloat() / goal).coerceIn(0f, 1f) else 0f
}

data class GymUiState(
    val username: String = "",

    val currentStreak: Int = 0,

    val daysThisWeek: Int = 0,
    val daysThisMonth: Int = 0,
    val daysThisYear: Int = 0,
    val weeklyGoal: Int = 4,
    val monthlyGoal: Int = 20,
    val yearlyGoal: Int = 0,

    val weekDays: List<WeekDay> = buildWeekDays(),

    val monthlyProgress: List<MonthProgress> = defaultMonthlyProgress(),

    val currentMonthIndex: Int = LocalDate.now().monthValue - 1,

    val editWeekDays: String = "",
    val editMonthDays: String = "",
    val editYearDays: String = "",
    val editWeeklyGoal: String = "",
    val editMonthlyGoal: String = "",
    val editYearlyGoal: String = "",

    val muscleCategories: List<String> = listOf(
        "Pecho", "Espalda", "Piernas", "Hombros",
        "Brazos", "Abdomen", "Cuerpo completo", "Cardio"
    ),

    val openDropdownIndex: Int = -1,

    val feedbackMessage: String = ""
)

fun buildWeekDays(): List<WeekDay> {
    // DayOfWeek: MONDAY=1 ... SUNDAY=7
    // Nuestro índice: Mon=0 ... Sun=6
    val todayIndex = LocalDate.now().dayOfWeek.value - 1
    return listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        .mapIndexed { index, name ->
            WeekDay(
                name = name,
                isChecked = false,
                muscleGroup = "",
                isToday = index == todayIndex
            )
        }
}

fun defaultMonthlyProgress(): List<MonthProgress> {
    val currentGoal = 20
    return listOf(
        MonthProgress("Jan",  1, 0, currentGoal, ""),
        MonthProgress("Feb",  2, 0, currentGoal, ""),
        MonthProgress("Mar",  3, 0, currentGoal, ""),
        MonthProgress("Apr",  4, 0, currentGoal, ""),
        MonthProgress("May",  5, 0, currentGoal, ""),
        MonthProgress("Jun",  6, 0, currentGoal, ""),
        MonthProgress("Jul",  7, 0, currentGoal, ""),
        MonthProgress("Aug",  8, 0, currentGoal, ""),
        MonthProgress("Sep",  9, 0, currentGoal, ""),
        MonthProgress("Oct", 10, 0, currentGoal, ""),
        MonthProgress("Nov", 11, 0, currentGoal, ""),
        MonthProgress("Dec", 12, 0, currentGoal, ""),
    )
}

fun daysInMonth(monthNumber: Int): Int {
    val year = LocalDate.now().year
    return YearMonth.of(year, monthNumber).lengthOfMonth()
}


class GymViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GymUiState())
    val uiState: StateFlow<GymUiState> = _uiState.asStateFlow()


    fun setUsername(name: String) {
        _uiState.update { it.copy(username = name) }
    }


    fun toggleDay(index: Int) {
        _uiState.update { state ->
            val updatedDays = state.weekDays.toMutableList()
            val day = updatedDays[index]
            val nowChecked = !day.isChecked

            updatedDays[index] = day.copy(
                isChecked = nowChecked,
                muscleGroup = if (!nowChecked) "" else day.muscleGroup
            )

            val checkedCount = updatedDays.count { it.isChecked }

            val updatedMonths = state.monthlyProgress.toMutableList()
            val currentIdx = state.currentMonthIndex
            val currentMonth = updatedMonths[currentIdx]

            val mostWorked = updatedDays
                .filter { it.isChecked && it.muscleGroup.isNotEmpty() }
                .groupBy { it.muscleGroup }
                .maxByOrNull { it.value.size }
                ?.key ?: currentMonth.mostWorked

            val prevChecked = state.weekDays.count { it.isChecked }
            val newMonthDays = (state.daysThisMonth - prevChecked + checkedCount)
                .coerceIn(0, daysInMonth(currentIdx + 1))

            updatedMonths[currentIdx] = currentMonth.copy(
                daysCount = newMonthDays,
                mostWorked = mostWorked
            )

            val newStreak = if (checkedCount > 0 && state.currentStreak == 0)
                1 else state.currentStreak

            state.copy(
                weekDays = updatedDays,
                monthlyProgress = updatedMonths,
                currentStreak = newStreak
            )
        }
    }
    fun selectMuscleGroup(dayIndex: Int, muscle: String) {
        _uiState.update { state ->
            val updatedDays = state.weekDays.toMutableList()
            updatedDays[dayIndex] = updatedDays[dayIndex].copy(muscleGroup = muscle)

            val mostWorked = updatedDays
                .filter { it.isChecked && it.muscleGroup.isNotEmpty() }
                .groupBy { it.muscleGroup }
                .maxByOrNull { it.value.size }
                ?.key ?: ""

            val updatedMonths = state.monthlyProgress.toMutableList()
            val currentIdx = state.currentMonthIndex
            if (mostWorked.isNotEmpty()) {
                updatedMonths[currentIdx] = updatedMonths[currentIdx].copy(
                    mostWorked = mostWorked
                )
            }

            state.copy(
                weekDays = updatedDays,
                monthlyProgress = updatedMonths,
                openDropdownIndex = -1
            )
        }
    }

    fun toggleDropdown(index: Int) {
        _uiState.update { state ->
            state.copy(
                openDropdownIndex = if (state.openDropdownIndex == index) -1 else index
            )
        }
    }

    fun onEditWeekDaysChange(v: String)    = _uiState.update { it.copy(editWeekDays = v) }
    fun onEditMonthDaysChange(v: String)   = _uiState.update { it.copy(editMonthDays = v) }
    fun onEditYearDaysChange(v: String)    = _uiState.update { it.copy(editYearDays = v) }
    fun onEditWeeklyGoalChange(v: String)  = _uiState.update { it.copy(editWeeklyGoal = v) }
    fun onEditMonthlyGoalChange(v: String) = _uiState.update { it.copy(editMonthlyGoal = v) }
    fun onEditYearlyGoalChange(v: String)  = _uiState.update { it.copy(editYearlyGoal = v) }

    fun saveWeekDays() {
        val value = _uiState.value.editWeekDays.toIntOrNull()
        when {
            value == null -> { showFeedback("⚠ Ingresa un número válido"); return }
            value < 0     -> { showFeedback("⚠ No puede ser negativo"); return }
            value > 7     -> { showFeedback("⚠ Máximo 7 días a la semana"); return }
        }
        _uiState.update { state ->
            val updatedMonths = state.monthlyProgress.toMutableList()
            val currentIdx = state.currentMonthIndex
            val diff = value!! - state.daysThisWeek
            val newMonthDays = (state.daysThisMonth + diff)
                .coerceIn(0, daysInMonth(currentIdx + 1))
            updatedMonths[currentIdx] = updatedMonths[currentIdx].copy(daysCount = newMonthDays)
            state.copy(
                daysThisWeek = value,
                daysThisMonth = newMonthDays,
                monthlyProgress = updatedMonths,
                editWeekDays = "",
                feedbackMessage = "✓ Días de la semana actualizados"
            )
        }
    }

    fun saveMonthDays() {
        val state = _uiState.value
        val maxMonth = daysInMonth(state.currentMonthIndex + 1)
        val value = state.editMonthDays.toIntOrNull()
        when {
            value == null    -> { showFeedback("⚠ Ingresa un número válido"); return }
            value < 0        -> { showFeedback("⚠ No puede ser negativo"); return }
            value > maxMonth -> { showFeedback("⚠ Máximo $maxMonth días este mes"); return }
        }
        _uiState.update { s ->
            val updatedMonths = s.monthlyProgress.toMutableList()
            updatedMonths[s.currentMonthIndex] = updatedMonths[s.currentMonthIndex]
                .copy(daysCount = value!!)
            s.copy(
                daysThisMonth = value,
                monthlyProgress = updatedMonths,
                editMonthDays = "",
                feedbackMessage = "✓ Días del mes actualizados"
            )
        }
    }

    fun saveYearDays() {
        val value = _uiState.value.editYearDays.toIntOrNull()
        when {
            value == null -> { showFeedback("⚠ Ingresa un número válido"); return }
            value < 0     -> { showFeedback("⚠ No puede ser negativo"); return }
            value > 365   -> { showFeedback("⚠ Máximo 365 días al año"); return }
        }
        _uiState.update { it.copy(
            daysThisYear = value!!,
            editYearDays = "",
            feedbackMessage = "✓ Días del año actualizados"
        )}
    }

    fun saveWeeklyGoal() {
        val value = _uiState.value.editWeeklyGoal.toIntOrNull()
        when {
            value == null -> { showFeedback("⚠ Ingresa un número válido"); return }
            value < 1     -> { showFeedback("⚠ La meta mínima es 1 día"); return }
            value > 7     -> { showFeedback("⚠ Máximo 7 días a la semana"); return }
        }
        _uiState.update { it.copy(
            weeklyGoal = value!!,
            editWeeklyGoal = "",
            feedbackMessage = "✓ Meta semanal actualizada"
        )}
    }

    fun saveMonthlyGoal() {
        val state = _uiState.value
        val maxMonth = daysInMonth(state.currentMonthIndex + 1)
        val value = state.editMonthlyGoal.toIntOrNull()
        when {
            value == null    -> { showFeedback("⚠ Ingresa un número válido"); return }
            value < 1        -> { showFeedback("⚠ La meta mínima es 1 día"); return }
            value > maxMonth -> { showFeedback("⚠ Máximo $maxMonth días este mes"); return }
        }
        _uiState.update { s ->
            // Actualiza la meta en todos los meses — igual que hace el backend
            val updatedMonths = s.monthlyProgress.map { it.copy(goal = value!!) }
            s.copy(
                monthlyGoal = value!!,
                monthlyProgress = updatedMonths,
                editMonthlyGoal = "",
                feedbackMessage = "✓ Meta mensual actualizada — barras recalculadas"
            )
        }
    }

    fun saveYearlyGoal() {
        val value = _uiState.value.editYearlyGoal.toIntOrNull()
        when {
            value == null -> { showFeedback("⚠ Ingresa un número válido"); return }
            value < 1     -> { showFeedback("⚠ La meta mínima es 1 día"); return }
            value > 365   -> { showFeedback("⚠ Máximo 365 días al año"); return }
        }
        _uiState.update { it.copy(
            yearlyGoal = value!!,
            editYearlyGoal = "",
            feedbackMessage = "✓ Meta anual actualizada"
        )}
    }

    private fun showFeedback(message: String) {
        _uiState.update { it.copy(feedbackMessage = message) }
    }

    fun clearFeedback() {
        _uiState.update { it.copy(feedbackMessage = "") }
    }
}