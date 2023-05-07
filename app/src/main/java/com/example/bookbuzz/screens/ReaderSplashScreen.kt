package com.example.bookbuzz.screens

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookbuzz.utils.ReaderAppTextStyle
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import com.example.bookbuzz.components.DotsPulsing
import com.example.bookbuzz.components.ReaderAppLogo
import com.example.bookbuzz.navigation.ReaderScreens
import com.example.bookbuzz.ui.theme.ExtraLightReaderColor
import com.example.bookbuzz.ui.theme.PrimaryReaderColor
import com.example.bookbuzz.ui.theme.SecondaryReaderColor
import com.example.bookbuzz.ui.theme.TertiaryReaderColor
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun ReaderSplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        val currUser=FirebaseAuth.getInstance().currentUser
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                })
        )
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        delay(2000)
        if (currUser!=null){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name){
                if (currentRoute != null) {
                    popUpTo(currentRoute){
                        inclusive=true
                    }
                }
            }
        } else{
        navController.navigate(ReaderScreens.LoginScreen.name) {
            if (currentRoute != null) {
                popUpTo(currentRoute) {
                    inclusive = true
                }
            }
        }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.linearGradient(
                listOf(
                    TertiaryReaderColor,
                    SecondaryReaderColor,
                    PrimaryReaderColor
                )
            )
        )) {


        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Surface(
                modifier = Modifier
                    .padding(15.dp)
                    .scale(scale = scale.value)
                    .size(330.dp),
                shape = RoundedCornerShape(33.dp),
                color = ExtraLightReaderColor,
                border = BorderStroke(2.dp, color = Color.Black)
            ) {
                Column(
                    modifier = Modifier.padding(1.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ReaderAppLogo()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "\"Read. Change. Repeat.\"", style = ReaderAppTextStyle.lightText)

                }
            }

            //m
            DotsPulsing()
        }
    }
}

