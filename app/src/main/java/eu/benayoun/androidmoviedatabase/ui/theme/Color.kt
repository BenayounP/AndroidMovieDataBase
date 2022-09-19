package eu.benayoun.androidmoviedatabase.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Some colors are inspired by Amélie Poulain french movie
// https://www.colourlovers.com/palette/187404/Am%C3%A9lie_Poulain

val vertAmelie = Color(0xFF006F1A)
val vertFonceAmelie = Color(0xFF015E40)
val orangeAmelie = Color(0xFFCA7900)

val red700= Color(0xFFD32F2F)
val lowAlphaRed900= Color(0x99B71C1C)

val Blue800= Color(0xFF1565C0)


val Grey50= Color(0xFFFAFAFA)
val Grey100= Color(0xFFF5F5F5)
val Grey200= Color(0xFFEEEEEE)
val Grey300= Color(0xFFE0E0E0)
val Grey400= Color(0xFFBDBDBD)
val Grey500= Color(0xFF9E9E9E)
val Grey600= Color(0xFF757575)
val Grey700= Color(0xFF616161)
val Grey800= Color(0xFF424242)
val Grey900= Color(0xFF212121)
val Black1000= Color(0xFF000000)
val White1000= Color(0xFFffffff)

class ComposeColors {
    companion object {

        @Composable
        fun updating() = getColor(light = Blue800, dark = Grey800)

        @Composable
        private fun getColor(light: Color, dark: Color) : Color {
            if (isDarkTheme()){
                return dark
            }
            else
            {
                return light
            }
        }

        @Composable
        fun isDarkTheme() = isSystemInDarkTheme()
    }
}



