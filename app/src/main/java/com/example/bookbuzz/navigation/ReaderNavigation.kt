package com.example.bookbuzz.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookbuzz.screens.ReaderSplashScreen
import com.example.bookbuzz.screens.details.BookReaderDetailsScreen
import com.example.bookbuzz.screens.home.Home
import com.example.bookbuzz.screens.login.ReaderLoginScreen
import com.example.bookbuzz.screens.search.ReaderBookSearchScreen
import com.example.bookbuzz.screens.stats.ReaderStatScreen
import com.example.bookbuzz.screens.update.ReaderUpdateScreen


@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name ){

         composable(ReaderScreens.SplashScreen.name) {
             ReaderSplashScreen(navController=navController)
         }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController=navController)
        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
           ReaderStatScreen(navController=navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            Home(navController=navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
           ReaderBookSearchScreen(navController=navController)
        }



        val detailName = ReaderScreens.DetailScreen.name
        composable(detailName){
            BookReaderDetailsScreen(navController=navController)
        }
        composable(ReaderScreens.UpdateScreen.name){
            ReaderUpdateScreen(navController=navController)
        }

    }

}