package com.example.habitforge.app.ui.gym

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habitforge.app.ui.theme.*

@Composable
fun GymScreen(viewModel: GymViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.feedbackMessage) {
        if (state.feedbackMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = state.feedbackMessage,
                duration = SnackbarDuration.Short
            )
            viewModel.clearFeedback()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Brown80,
                    contentColor = CreamWhite,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        containerColor = BeigeBase
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WelcomeHeader(username = state.username)

            StreakCard(
                weekDays = state.weekDays,
                currentStreak = state.currentStreak
            )

            // Stats
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("Días\nesta semana", state.daysThisWeek.toString(), Modifier.weight(1f))
                StatCard("Meta días\nsemana",  state.weeklyGoal.toString(),  Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("Días\neste mes",  state.daysThisMonth.toString(), Modifier.weight(1f))
                StatCard("Meta días\nmes",  state.monthlyGoal.toString(),   Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("Días\neste año",  state.daysThisYear.toString(),                           Modifier.weight(1f))
                StatCard("Meta días\naño",  if (state.yearlyGoal == 0) "—" else state.yearlyGoal.toString(), Modifier.weight(1f))
            }

            WeekDaysCard(
                weekDays = state.weekDays,
                muscleCategories = state.muscleCategories,
                openDropdownIndex = state.openDropdownIndex,
                onToggleDay = { viewModel.toggleDay(it) },
                onSelectMuscle = { day, muscle -> viewModel.selectMuscleGroup(day, muscle) },
                onToggleDropdown = { viewModel.toggleDropdown(it) }
            )

            MonthlyProgressCard(
                monthlyProgress = state.monthlyProgress,
                currentMonthIndex = state.currentMonthIndex
            )

            // Editar días
            EditSection("Editar Días entrenados esta semana", state.editWeekDays,
                "Máx 7 días", { viewModel.onEditWeekDaysChange(it) }) { viewModel.saveWeekDays() }

            EditSection("Editar Días entrenados este mes", state.editMonthDays,
                "Máx ${daysInMonth(state.currentMonthIndex + 1)} días", { viewModel.onEditMonthDaysChange(it) }) { viewModel.saveMonthDays() }

            EditSection("Editar Días entrenados este año", state.editYearDays,
                "Máx 365 días", { viewModel.onEditYearDaysChange(it) }) { viewModel.saveYearDays() }

            // Editar metas
            EditSection("Editar meta semanal", state.editWeeklyGoal,
                "1 – 7 días", { viewModel.onEditWeeklyGoalChange(it) }) { viewModel.saveWeeklyGoal() }

            EditSection("Editar meta mensual", state.editMonthlyGoal,
                "1 – ${daysInMonth(state.currentMonthIndex + 1)} días", { viewModel.onEditMonthlyGoalChange(it) }) { viewModel.saveMonthlyGoal() }

            EditSection("Editar meta anual", state.editYearlyGoal,
                "1 – 365 días", { viewModel.onEditYearlyGoalChange(it) }) { viewModel.saveYearlyGoal() }

            AnnualGoalCard(
                daysThisYear = state.daysThisYear,
                yearlyGoal = state.yearlyGoal
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ============================================================
// COMPONENTES
// ============================================================

@Composable
private fun WelcomeHeader(username: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Bienvenid@, $username",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text("RUTINAS DEL GIMNASIO",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary, letterSpacing = 1.5.sp)
        }
    }
}

@Composable
private fun StreakCard(weekDays: List<WeekDay>, currentStreak: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Racha de entrenamiento",
                style = MaterialTheme.typography.titleSmall, color = TextSecondary)
            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                weekDays.forEach { day ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = day.name,
                            style = MaterialTheme.typography.labelSmall,
                            // Hoy aparece en marrón, el resto gris
                            color = if (day.isToday) Brown80 else TextSecondary,
                            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 10.sp
                        )
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    when {
                                        day.isChecked -> Brown80
                                        day.isToday   -> BeigeDeep
                                        else          -> BeigeDeep
                                    }
                                )
                                .border(
                                    width = if (day.isToday) 2.dp else 1.dp,
                                    color = if (day.isChecked) Brown60
                                    else if (day.isToday) Brown80
                                    else Brown40,
                                    shape = RoundedCornerShape(50)
                                )
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(currentStreak.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = TextPrimary, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Text("🔥", fontSize = 32.sp)
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall,
                color = TextSecondary, fontSize = 10.sp, lineHeight = 13.sp)
        }
    }
}

@Composable
private fun WeekDaysCard(
    weekDays: List<WeekDay>,
    muscleCategories: List<String>,
    openDropdownIndex: Int,
    onToggleDay: (Int) -> Unit,
    onSelectMuscle: (Int, String) -> Unit,
    onToggleDropdown: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Días entrenados en la semana",
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))

            weekDays.forEachIndexed { index, day ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = day.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (day.isToday) Brown80 else TextSecondary,
                        fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.width(32.dp)
                    )
                    Checkbox(
                        checked = day.isChecked,
                        onCheckedChange = { onToggleDay(index) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Brown80,
                            uncheckedColor = Brown40
                        ),
                        modifier = Modifier.size(20.dp)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { if (day.isChecked) onToggleDropdown(index) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            enabled = day.isChecked,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (day.muscleGroup.isEmpty()) TextHint else TextPrimary,
                                disabledContentColor = TextHint.copy(alpha = 0.3f)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (day.isChecked) Brown40 else Brown40.copy(alpha = 0.3f)
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (day.muscleGroup.isEmpty()) "Categoría" else day.muscleGroup,
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1, overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.ArrowDropDown, null,
                                modifier = Modifier.size(16.dp), tint = TextHint)
                        }
                        DropdownMenu(
                            expanded = openDropdownIndex == index,
                            onDismissRequest = { onToggleDropdown(index) },
                            modifier = Modifier.background(CreamWhite)
                        ) {
                            muscleCategories.forEach { muscle ->
                                DropdownMenuItem(
                                    text = { Text(muscle, style = MaterialTheme.typography.bodyMedium, color = TextPrimary) },
                                    onClick = { onSelectMuscle(index, muscle) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthlyProgressCard(
    monthlyProgress: List<MonthProgress>,
    currentMonthIndex: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Progreso mensual del gym",
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))

            monthlyProgress.forEach { month ->
                val isCurrentMonth = month.monthNumber - 1 == currentMonthIndex
                val animatedProgress by animateFloatAsState(
                    targetValue = month.progress,
                    animationSpec = tween(600),
                    label = "progress_${month.month}"
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = month.month,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isCurrentMonth) Brown80 else TextSecondary,
                        fontWeight = if (isCurrentMonth) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.width(28.dp)
                    )
                    Box(
                        modifier = Modifier.weight(1f).height(10.dp)
                            .clip(RoundedCornerShape(50)).background(BeigeDeep)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxHeight()
                                .fillMaxWidth(animatedProgress)
                                .clip(RoundedCornerShape(50))
                                .background(if (isCurrentMonth) FireOrange else Brown80)
                        )
                    }
                }
                if (month.mostWorked.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 36.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("💪", fontSize = 10.sp)
                        Text("Más trabajado: ${month.mostWorked}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextHint, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun EditSection(
    title: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium,
                color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text(placeholder,
                        style = MaterialTheme.typography.bodySmall, color = TextHint) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onSave() }),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brown80, unfocusedBorderColor = Brown40,
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        cursorColor = Brown80,
                        focusedContainerColor = CreamWhite, unfocusedContainerColor = CreamWhite
                    )
                )
                Button(
                    onClick = onSave,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Brown80, contentColor = CreamWhite),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text("Editar", style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun AnnualGoalCard(daysThisYear: Int, yearlyGoal: Int) {
    val progress = if (yearlyGoal > 0)
        (daysThisYear.toFloat() / yearlyGoal).coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(600),
        label = "annual_progress"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("🎯", fontSize = 16.sp)
                    Text("Meta anual de entrenamiento",
                        style = MaterialTheme.typography.titleSmall,
                        color = TextPrimary, fontWeight = FontWeight.SemiBold)
                }
                Text("$daysThisYear / ${if (yearlyGoal == 0) "—" else yearlyGoal} días",
                    style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            }
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(12.dp)
                    .clip(RoundedCornerShape(50)).background(BeigeDeep)
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(animatedProgress)
                        .clip(RoundedCornerShape(50)).background(Brown80)
                )
            }
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Progreso acumulado del año",
                    style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                Text("${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}