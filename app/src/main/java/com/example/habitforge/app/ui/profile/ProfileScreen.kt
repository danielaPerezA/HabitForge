package com.example.habitforge.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitforge.app.session.AppSession
import com.example.habitforge.app.ui.theme.*

@Composable
fun ProfileScreen(
    username: String = AppSession.displayName(),
    onLogout: () -> Unit
) {
    var firstName     by remember { mutableStateOf(AppSession.firstName) }
    var lastName      by remember { mutableStateOf(AppSession.lastName) }
    var age           by remember { mutableStateOf("") }
    var phone         by remember { mutableStateOf("") }
    var usernameField by remember { mutableStateOf(AppSession.username) }

    var weeklyGoal    by remember { mutableStateOf("4") }
    var monthlyGoal   by remember { mutableStateOf("20") }

    var personalExpanded by remember { mutableStateOf(true) }
    var goalsExpanded    by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var savedMessage     by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BeigeBase, BeigeDark, BeigeBase)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (savedMessage.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SuccessGreen.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(savedMessage,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = SuccessGreen)
                }
            }

            ProfileSection(
                title = "Mis datos personales",
                isExpanded = personalExpanded,
                onToggle = { personalExpanded = !personalExpanded }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ProfileField("Nombres", firstName, { firstName = it }, "Tu nombre",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

                    ProfileField("Apellidos", lastName, { lastName = it }, "Tus apellidos",
                        imeAction = ImeAction.Next,
                        onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(Modifier.weight(1f)) {
                            ProfileField("Edad", age, { age = it }, "19",
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })
                        }
                        Column(Modifier.weight(1f)) {
                            ProfileField("Número de celular", phone, { phone = it }, "300...",
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })
                        }
                    }

                    ProfileField(
                        "Usuario ¿Cómo quieres que te llamemos?",
                        usernameField, { usernameField = it },
                        "Tu apodo",
                        imeAction = ImeAction.Done,
                        onImeAction = { focusManager.clearFocus() })

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            AppSession.login(
                                username = usernameField.ifEmpty { firstName },
                                firstName = firstName,
                                lastName = lastName,
                                email = AppSession.email
                            )
                            savedMessage = "✓ Datos guardados correctamente"
                        },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Brown80, contentColor = CreamWhite),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text("Guardar datos  →",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            ProfileSection(
                title = "Mis metas",
                isExpanded = goalsExpanded,
                onToggle = { goalsExpanded = !goalsExpanded }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("🏋️", fontSize = 16.sp)
                        Text("Meta de días en el gym",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(Modifier.weight(1f)) {
                            ProfileField("Semanal", weeklyGoal, { weeklyGoal = it }, "1–7",
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })
                        }
                        Column(Modifier.weight(1f)) {
                            ProfileField("Mensual", monthlyGoal, { monthlyGoal = it }, "1–31",
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                                onImeAction = { focusManager.clearFocus() })
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            savedMessage = "✓ Metas guardadas correctamente"
                        },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Brown80, contentColor = CreamWhite),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text("Guardar metas  →",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ErrorRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("Cerrar sesión",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(80.dp))
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = CreamWhite,
            shape = RoundedCornerShape(20.dp),
            title = {
                Text("¿Cerrar sesión?",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary, fontWeight = FontWeight.SemiBold)
            },
            text = {
                Text("Tu progreso está guardado. Puedes volver cuando quieras.",
                    style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            },
            confirmButton = {
                Button(
                    onClick = { showLogoutDialog = false; onLogout() },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ErrorRed, contentColor = CreamWhite)
                ) {
                    Text("Sí, cerrar sesión",
                        style = MaterialTheme.typography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar",
                        style = MaterialTheme.typography.labelLarge, color = TextSecondary)
                }
            }
        )
    }
}

@Composable
private fun ProfileSection(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
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
                Text(title, style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = onToggle, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = null, tint = TextSecondary
                    )
                }
            }
            if (isExpanded) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = BeigeDeep, thickness = 1.dp)
                Spacer(Modifier.height(12.dp))
                content()
            }
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall,
            color = TextSecondary, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder,
                style = MaterialTheme.typography.bodySmall, color = TextHint) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onNext = { onImeAction() }, onDone = { onImeAction() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Brown80, unfocusedBorderColor = Brown40,
                focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                cursorColor = Brown80,
                focusedContainerColor = CreamWhite, unfocusedContainerColor = CreamWhite
            ),
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}