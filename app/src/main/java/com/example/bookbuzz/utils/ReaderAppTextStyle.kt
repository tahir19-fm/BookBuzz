package com.example.bookbuzz.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.bookbuzz.R

object ReaderAppTextStyle {
    val headtext= TextStyle(color = Color.Black, fontFamily =FontFamilyReaderApp.semiBold, fontSize = 44.sp)
    val subText=TextStyle(color = Color.Black, fontFamily =FontFamilyReaderApp.regular, fontSize = 16.sp)
    val lightText=TextStyle(color = Color.Black.copy(alpha = 0.8f), fontFamily =FontFamilyReaderApp.light, fontSize = 16.sp)
}
object FontFamilyReaderApp{
    val regular= FontFamily(Font(R.font.mona_sans_regular))
    val black= FontFamily(Font(R.font.poppin_black))
    val light= FontFamily(Font(R.font.mona_sans_light))
    val medium= FontFamily(Font(R.font.mona_sans_medium))
    val semiBold= FontFamily(Font(R.font.mona_sans_semi_bold))
    val ultraLight= FontFamily(Font(R.font.mona_sans_ultra_light))
    val blackItalic= FontFamily(Font(R.font.mons_sans_black_italic))
    val bold= FontFamily(Font(R.font.mons_sans_bold))
    val extraBold= FontFamily(Font(R.font.mons_sans_extra_bold))
}