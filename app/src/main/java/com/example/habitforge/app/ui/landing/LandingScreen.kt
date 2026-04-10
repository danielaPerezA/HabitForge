package com.example.habitforge.app.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitforge.app.ui.theme.BeigeBase
import com.example.habitforge.app.ui.theme.BeigeDark
import com.example.habitforge.app.ui.theme.Brown20
import com.example.habitforge.app.ui.theme.Brown40
import com.example.habitforge.app.ui.theme.Brown60
import com.example.habitforge.app.ui.theme.Brown80
import com.example.habitforge.app.ui.theme.CreamWhite
import com.example.habitforge.app.ui.theme.FireAmber
import com.example.habitforge.app.ui.theme.TextPrimary
import com.example.habitforge.app.ui.theme.TextSecondary

@Composable
fun LandingScreen(
    onLoginClick: () -> Unit,
    onGetStartedClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        BeigeBase,
                        BeigeDark,
                        BeigeBase
                    )
                )
            )
    ) {
        LandingBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
        ) {
            LandingTopBar(onLoginClick = onLoginClick)

            HeroSection(onGetStartedClick = onGetStartedClick)

            Spacer(modifier = Modifier.height(32.dp))

            FeaturesSection()

            Spacer(modifier = Modifier.height(40.dp))

            LandingFooter()
        }
    }
}

@Composable
private fun LandingTopBar(onLoginClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brown80)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo + nombre
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "⛵", fontSize = 20.sp)
            Text(
                text = "HabitForge",
                style = MaterialTheme.typography.titleLarge,
                color = CreamWhite,
                fontWeight = FontWeight.SemiBold
            )
        }

        OutlinedButton(
            onClick = onLoginClick,
            shape = RoundedCornerShape(50),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CreamWhite),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = CreamWhite
            ),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.labelLarge,
                color = CreamWhite
            )
        }
    }
}

@Composable
private fun HeroSection(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Brown80.copy(alpha = 0.85f)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "The Adventure of\nbeing you",
                    style = MaterialTheme.typography.headlineMedium,
                    color = CreamWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "your next favorite app",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Brown20,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CreamWhite),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Convierte tus metas en logros reales con ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
                Text(
                    text = "HabitForge",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Brown80,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ". Trabaja en tu fitness, sigue tu progreso, mantén tus rachas y construye la mejor versión de ti.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("🌱", "🌿", "🪴", "🌳").forEach { emoji ->
                        Text(text = emoji, fontSize = 22.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Brown80,
                        contentColor = CreamWhite
                    ),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Text(
                        text = "Get Started  →",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Qué puedes hacer en HabitForge?",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        FeatureCard(
            emoji = "🏋️",
            title = "Gestión de Gym",
            description = "Registra tus días de entrenamiento, define metas semanales y mensuales, y mantén una racha activa que te motive a no fallar."
        )

        Spacer(modifier = Modifier.height(12.dp))

        FeatureCard(
            emoji = "🔥",
            title = "Sistema de Rachas",
            description = "Construye hábitos reales con un contador de rachas diarias. Si estás a punto de perder la tuya, HabitForge te avisa."
        )
    }
}

@Composable
private fun FeatureCard(
    emoji: String,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BeigeDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun LandingFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brown80)
            .padding(vertical = 24.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.width(40.dp),
            color = Brown20,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "THE ADVENTURE OF BEING YOU",
            style = MaterialTheme.typography.labelSmall,
            color = Brown20,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "© 2025 HabitForge · Todos los derechos reservados",
            style = MaterialTheme.typography.labelSmall,
            color = Brown20.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun LandingBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 150.dp, y = (-80).dp)
                .clip(RoundedCornerShape(50))
                .background(Brown40.copy(alpha = 0.07f))
                .align(Alignment.TopEnd)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-60).dp, y = 60.dp)
                .clip(RoundedCornerShape(50))
                .background(Brown60.copy(alpha = 0.06f))
                .align(Alignment.BottomStart)
        )
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = 40.dp, y = (-20).dp)
                .clip(RoundedCornerShape(50))
                .background(FireAmber.copy(alpha = 0.05f))
                .align(Alignment.CenterEnd)
        )
    }
}