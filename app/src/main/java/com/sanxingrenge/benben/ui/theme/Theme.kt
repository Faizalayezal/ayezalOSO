package com.sanxingrenge.benben.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.sanxingrenge.benben.R

private val LightColorPalette = lightColors(
    primary = VioletBlue,
    primaryVariant = Cinder,
    secondary = DarkTurquoise
)

@Composable
fun OsomateTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        /*DarkColorPalette*/
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val poppinsBold = FontFamily(
    Font(R.font.poppins_bold)
)
val poppinsExtraBold = FontFamily(
    Font(R.font.poppins_extrabold)
)
val poppinsExtraLight = FontFamily(
    Font(R.font.poppins_extralight)
)
val poppinsLight = FontFamily(
    Font(R.font.poppins_light)
)
val poppinsMedium = FontFamily(
    Font(R.font.poppins_medium)
)
val poppinsRegular = FontFamily(
    Font(R.font.poppins_regular)
)
val poppinsSemiBold = FontFamily(
    Font(R.font.poppins_semibold)
)
val poppinsThin = FontFamily(
    Font(R.font.poppins_thin)
)
