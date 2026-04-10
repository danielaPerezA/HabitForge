package com.example.habitforge.app.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitforge.app.session.AppSession
import com.example.habitforge.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BeigeBase, BeigeDark, BeigeBase)
                )
            )
    ) {
        AuthBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
        ) {
            AuthTopBar()

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HabitForge",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "THE ADVENTURE BEGINS WITH YOU.",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    letterSpacing = 1.5.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(containerColor = BeigeDark),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    AuthTabs(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    AnimatedContent(
                        targetState = selectedTab,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "auth_form"
                    ) { tab ->
                        when (tab) {
                            0 -> LoginForm(onLoginSuccess = onLoginSuccess)
                            1 -> RegisterForm(
                                onRegisterSuccess = {
                                    // Tras registrarse va al tab Login
                                    selectedTab = 0
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "← Back to main page",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ============================================================
// TABS
// ============================================================
@Composable
private fun AuthTabs(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BeigeDeep, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        listOf("Login", "Register").forEachIndexed { index, label ->
            val isSelected = selectedTab == index
            Button(
                onClick = { onTabSelected(index) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Brown80 else BeigeDeep,
                    contentColor = if (isSelected) CreamWhite else TextSecondary
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

// ============================================================
// FORMULARIO LOGIN
// ============================================================
@Composable
private fun LoginForm(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AuthLabel("EMAIL")
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "tu@email.com",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )

        AuthLabel("PASSWORD")
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "••••••••",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = TextHint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Brown80, uncheckedColor = Brown40),
                modifier = Modifier.size(20.dp)
            )
            Text("Remember Password",
                style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                // Simula login — extrae username del email (antes del @)
                // DESPUÉS: reemplazar con llamada real a /auth/login
                val name = email.substringBefore("@").ifEmpty { "Usuario" }
                AppSession.login(username = name, email = email)
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Brown80, contentColor = CreamWhite),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text("ACCESS  →",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
        }
    }
}

// ============================================================
// FORMULARIO REGISTER
// ============================================================
@Composable
private fun RegisterForm(onRegisterSuccess: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AuthLabel("NOMBRE")
        AuthTextField(firstName, { firstName = it }, "Tu nombre",
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

        AuthLabel("APELLIDOS")
        AuthTextField(lastName, { lastName = it }, "Tus apellidos",
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

        AuthLabel("USUARIO")
        AuthTextField(username, { username = it }, "¿Cómo quieres que te llamemos?",
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

        AuthLabel("EMAIL")
        AuthTextField(email, { email = it }, "tu@email.com",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) })

        AuthLabel("CONTRASEÑA")
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "••••••••",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = TextHint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        AuthLabel("CONFIRMAR CONTRASEÑA")
        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "••••••••",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() },
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPassword.isNotEmpty() && password != confirmPassword
        )

        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            Text("Las contraseñas no coinciden",
                style = MaterialTheme.typography.labelSmall, color = ErrorRed)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                if (password == confirmPassword && firstName.isNotEmpty()) {
                    // Guarda en sesión local
                    // DESPUÉS: reemplazar con llamada real a /auth/register
                    AppSession.login(
                        username = username.ifEmpty { firstName },
                        firstName = firstName,
                        lastName = lastName,
                        email = email
                    )
                    onRegisterSuccess() // vuelve al tab Login
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Brown80, contentColor = CreamWhite),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text("REGISTRARSE  →",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
        }
    }
}

// ============================================================
// COMPONENTES COMPARTIDOS
// ============================================================
@Composable
private fun AuthLabel(text: String) {
    Text(text, style = MaterialTheme.typography.labelSmall,
        color = TextSecondary, letterSpacing = 1.sp, fontWeight = FontWeight.SemiBold)
}

@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder,
            style = MaterialTheme.typography.bodySmall, color = TextHint) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() }, onDone = { onImeAction() }),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Brown80, unfocusedBorderColor = Brown40,
            errorBorderColor = ErrorRed,
            focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
            cursorColor = Brown80,
            focusedContainerColor = CreamWhite, unfocusedContainerColor = CreamWhite,
            errorContainerColor = CreamWhite
        )
    )
}

@Composable
private fun AuthTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brown80)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("⛵", fontSize = 20.sp)
            Text("HabitForge", style = MaterialTheme.typography.titleLarge,
                color = CreamWhite, fontWeight = FontWeight.SemiBold)
        }
        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(50),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CreamWhite),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = CreamWhite),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text("Login", style = MaterialTheme.typography.labelLarge, color = CreamWhite)
        }
    }
}

@Composable
private fun AuthBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.size(280.dp).offset(x = 120.dp, y = (-60).dp)
            .background(Brown40.copy(alpha = 0.07f), RoundedCornerShape(50))
            .align(Alignment.TopEnd))
        Box(modifier = Modifier.size(180.dp).offset(x = (-50).dp, y = 80.dp)
            .background(Brown60.copy(alpha = 0.06f), RoundedCornerShape(50))
            .align(Alignment.BottomStart))
    }
}