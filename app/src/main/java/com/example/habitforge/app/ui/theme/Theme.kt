@file:Suppress("DEPRECATION")

package com.example.habitforge.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val Brown80     = Color(0xFF8B5E3C)
val Brown60     = Color(0xFFA0714F)
val Brown40     = Color(0xFFBF9470)
val Brown20     = Color(0xFFDDBB98)

val BeigeBase   = Color(0xFFF5ECD7)
val BeigeDark   = Color(0xFFEDE0C8)
val BeigeDeep   = Color(0xFFE0CFA8)
val CreamWhite  = Color(0xFFFAF5EC)

// Texto
val TextPrimary   = Color(0xFF2E1A0E)
val TextSecondary = Color(0xFF6B4226)
val TextHint      = Color(0xFF9E7355)

val FireOrange    = Color(0xFFE8593C)
val FireAmber     = Color(0xFFF2A623)
val StreakGold    = Color(0xFFBA7517)

// Estado
val SuccessGreen  = Color(0xFF4CAF50)
val WarningYellow = Color(0xFFF9CB42)
val ErrorRed      = Color(0xFFD32F2F)

val BrownDark80   = Color(0xFF3E2010)
val BrownDark60   = Color(0xFF5C3018)
val BrownDark40   = Color(0xFF7A4020)
val BeigeDarkMode = Color(0xFF1E120A)

private val LightColorScheme = lightColorScheme(
    primary          = Brown80,
    onPrimary        = CreamWhite,
    primaryContainer = BeigeDark,
    onPrimaryContainer = TextPrimary,

    secondary          = Brown60,
    onSecondary        = CreamWhite,
    secondaryContainer = BeigeDeep,
    onSecondaryContainer = TextSecondary,

    tertiary          = FireOrange,
    onTertiary        = CreamWhite,
    tertiaryContainer = Color(0xFFFDE8D8),
    onTertiaryContainer = Color(0xFF6B1A00),

    background        = BeigeBase,
    onBackground      = TextPrimary,
    surface           = CreamWhite,
    onSurface         = TextPrimary,
    surfaceVariant    = BeigeDark,
    onSurfaceVariant  = TextSecondary,

    outline           = Brown40,
    outlineVariant    = BeigeDeep,

    error             = ErrorRed,
    onError           = Color.White,

    surfaceContainer        = BeigeDark,
    surfaceContainerHigh    = BeigeDeep,
    surfaceContainerHighest = Color(0xFFD8C89A),
    surfaceContainerLow     = BeigeBase,
    surfaceContainerLowest  = CreamWhite,
)

private val DarkColorScheme = darkColorScheme(
    primary          = Brown20,
    onPrimary        = BrownDark80,
    primaryContainer = BrownDark60,
    onPrimaryContainer = Brown20,

    secondary          = Brown40,
    onSecondary        = BrownDark80,
    secondaryContainer = BrownDark40,
    onSecondaryContainer = Brown20,

    tertiary          = FireAmber,
    onTertiary        = Color(0xFF3E1200),
    tertiaryContainer = Color(0xFF6B2800),
    onTertiaryContainer = Color(0xFFFFDBC9),

    background        = BeigeDarkMode,
    onBackground      = Color(0xFFF0E0C8),
    surface           = BrownDark80,
    onSurface         = Color(0xFFF0E0C8),
    surfaceVariant    = BrownDark60,
    onSurfaceVariant  = Color(0xFFCCAA88),

    outline           = Brown60,
    outlineVariant    = BrownDark40,

    error             = Color(0xFFFFB4AB),
    onError           = Color(0xFF690005),
)

@Composable
fun HabitForgeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HabitForgeTypography,
        shapes = HabitForgeShapes,
        content = content
    )
}