package com.example.bookbuzz.screens.home

import android.window.SplashScreen
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.bookbuzz.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun Home(navController: NavHostController) {
    val currScreen= navController.currentBackStackEntry?.destination?.route

    Text(text = "HOnme", modifier = Modifier.clickable{
        FirebaseAuth.getInstance().signOut()
        navController.navigate(ReaderScreens.SplashScreen.name){
            if (currScreen != null) {
                popUpTo(currScreen){
                    inclusive=true
                }
            }
            launchSingleTop=true
        }
    })

}