package com.recipeapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.recipeapp.R

val lato = FontFamily(
    Font(R.font.lato_light, FontWeight.W300),
    Font(R.font.lato_regular, FontWeight.W400),
    Font(R.font.lato_black, FontWeight.W500),
    Font(R.font.lato_bold, FontWeight.W600)
)

val latoTypography = Typography(
    /*h1 = TextStyle(fontFamily = lato, fontWeight = FontWeight.W500, fontSize = 30.sp),
    h2 = TextStyle(fontFamily = lato, fontWeight = FontWeight.W500, fontSize = 24.sp),
    h3 = TextStyle(fontFamily = lato, fontWeight = FontWeight.W500, fontSize = 20.sp),
    h4 = TextStyle(fontFamily = lato, fontWeight = FontWeight.W400, fontSize = 18.sp),
    h5 = TextStyle(fontFamily = lato, fontWeight = FontWeight.W400, fontSize = 16.sp),
    h6 = TextStyle(fontFamily = lato, fontWeight = FontWeight.W400, fontSize = 14.sp),*/
    titleMedium = TextStyle(fontFamily = lato, fontWeight = FontWeight.W400, fontSize = 16.sp),
    titleSmall = TextStyle(fontFamily = lato, fontWeight = FontWeight.W400, fontSize = 14.sp),
    bodyLarge = TextStyle(fontFamily = lato, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = lato, fontSize = 14.sp),
    labelSmall = TextStyle(fontFamily = lato, fontWeight = FontWeight.Normal, fontSize = 12.sp),
)