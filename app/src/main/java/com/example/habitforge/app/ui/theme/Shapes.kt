package com.example.habitforge.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val HabitForgeShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),

    small = RoundedCornerShape(10.dp),

    medium = RoundedCornerShape(16.dp),

    large = RoundedCornerShape(20.dp),

    extraLarge = RoundedCornerShape(28.dp),
)

val PillShape = RoundedCornerShape(50)

val BottomSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

val StatCardShape = RoundedCornerShape(16.dp)


object Dimens {
    val screenPadding     = 16.dp
    val screenPaddingLg   = 24.dp

    // Padding interno de cards
    val cardPadding       = 16.dp
    val cardPaddingLg     = 20.dp

    // Gap entre elementos
    val spacingXs  = 4.dp
    val spacingSm  = 8.dp
    val spacingMd  = 12.dp
    val spacingLg  = 16.dp
    val spacingXl  = 24.dp
    val spacingXxl = 32.dp

    // Alturas fijas
    val navBarHeight     = 56.dp
    val buttonHeight     = 52.dp
    val statCardHeight   = 80.dp
    val dayCheckboxSize  = 24.dp
    val streakNumberSize = 72.dp
}