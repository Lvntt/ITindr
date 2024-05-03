package dev.lantt.itindr.presentation.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Magenta

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PrimaryColor = Color(0xFFFA13AB)
val Magenta = Color(0xFFE813FA)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Gray = Color(0xFFF3F3F3)
val DarkGray = Color(0xFFA59EA3)
val DoveGray = Color(0xFF716A6F)
val GalleryGray = Color(0xFFF0ECEF)

val PrimaryHorizontalGradient = Brush.linearGradient(
    colors = listOf(
        PrimaryColor,
        Magenta
    )
)
val PrimaryDiagonalGradient = Brush.linearGradient(
    colors = listOf(
        PrimaryColor,
        Magenta
    ),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)