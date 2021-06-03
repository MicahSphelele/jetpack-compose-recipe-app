package com.recipeapp.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.recipeapp.R

val Lato = FontFamily(
    Font(R.font.lato_light, FontWeight.W300),
    Font(R.font.lato_regular, FontWeight.W400),
    Font(R.font.lato_black, FontWeight.W500),
    Font(R.font.lato_bold, FontWeight.W600)
)

val LatoTypography = Typography(
    h1 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W500, fontSize = 30.sp),
    h2 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W500, fontSize = 24.sp),
    h3 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W500, fontSize = 20.sp),
    h4 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W400, fontSize = 18.sp),
    h5 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W400, fontSize = 16.sp),
    h6 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W400, fontSize = 14.sp),
    subtitle1 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W400, fontSize = 16.sp),
    subtitle2 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W400, fontSize = 14.sp),
    body1 = TextStyle(fontFamily = Lato, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    body2 = TextStyle(fontFamily = Lato, fontSize = 14.sp),
    button = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.W400,
        color = Color.White,
        fontSize = 15.sp
    ),
    caption = TextStyle(fontFamily = Lato, fontWeight = FontWeight.Normal, fontSize = 12.sp),
    overline = TextStyle(fontFamily = Lato, fontWeight = FontWeight.W400, fontSize = 12.sp)
)