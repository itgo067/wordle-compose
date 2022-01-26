package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColors(
    primary = Color(0xFF689F38),
    secondary = Color(0xFFFFA000),
    onBackground = Color(0xFFD1D1D1),
    onPrimary = Color(0xFF2A2A2A),
)

private val LightColors = lightColors(
    primary = Color(0xFF689F38),
    secondary = Color(0xFFFFA000),
    onBackground = Color(0xFFD1D1D1),
    onPrimary = Color(0xFFD1D1D1),

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val Colors.correctBackground @Composable get() = Color(0xFF4CAF50)
val Colors.wrongPositionBackground @Composable get() = Color(0xFFFFA000)
val Colors.incorrectBackground @Composable get() = Color(0xFF5B5B5B)
val Colors.enteringBackground @Composable get() = MaterialTheme.colors.background
val Colors.keyboard @Composable get() = Color(0xFF393939)
val Colors.keyboardDisabled @Composable get() = Color(0xFF642424)
val Colors.onKeyboard @Composable get() = Color(0xFFE7E7E7)

@Composable
fun WordleTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colors = when {
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(colors = colors, typography = Typography, content = content)
}