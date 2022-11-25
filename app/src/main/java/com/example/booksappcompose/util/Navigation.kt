package com.example.booksappcompose.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booksappcompose.presentation.favourites.FavouritesScreen
import com.example.booksappcompose.presentation.home.HomeScreen
import com.example.booksappcompose.presentation.notification.NotificationScreen
import com.example.booksappcompose.presentation.on_boarding.OnBoardingScreen
import com.example.booksappcompose.presentation.person.PersonScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState,
    sheetState: ModalBottomSheetState
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OnBoarding.route
    ) {
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(
                navController = navController
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(scaffoldState = scaffoldState, sheetState = sheetState)
        }
        composable(route = Screen.Favourites.route) {
            FavouritesScreen()
        }

        composable(route = Screen.Notification.route) {
            NotificationScreen()
        }
        composable(route = Screen.Person.route) {
            PersonScreen()
        }
    }
}