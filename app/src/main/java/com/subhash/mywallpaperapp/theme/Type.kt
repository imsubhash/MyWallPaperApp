
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

//val robotoCus = Typography(
//    body1 = TextStyle(
//        fontFamily = FontFamily(
//            fonts = listOf(
//                Font(R.font.roboto_light, weight = FontWeight.W300),
//            )
//        ),
//        fontWeight = FontWeight.SemiBold
//    ),
//    subtitle2 = TextStyle(
//        fontFamily = FontFamily(
//            fonts = listOf(
//                Font(R.font.fugaz_one, weight = FontWeight.W300)
//            )
//        )
//    )
//)

val titleStyle = TextStyle(
    shadow = Shadow(
        color = Color(0xFFE0E0E0),
        blurRadius = 40f
    ),
)

val searchBarStyle = TextStyle(
    fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.roboto_regular, FontWeight.Light)
        )
    )
)

val fontFamilyBungee = FontFamily(
    fonts = listOf(
        Font(R.font.bungeeshade_regular, FontWeight.Normal),
    )
)