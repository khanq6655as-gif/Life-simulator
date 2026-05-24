package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GameColorScheme = lightColorScheme(
    primary = RetroGold,
    secondary = SkillBlue,
    tertiary = HustleGreen,
    background = SlateDarkBackground,
    surface = SlateCard,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextLight,
    onSurface = TextLight,
    surfaceVariant = SlateCardSelected,
    onSurfaceVariant = TextLight
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = GameColorScheme,
        typography = Typography,
        content = content
    )
}
