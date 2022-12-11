package com.example.booksappcompose.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.booksappcompose.presentation.favourites.FavouritesScreen
import com.example.booksappcompose.presentation.home.HomeScreen
import com.example.booksappcompose.presentation.notification.NotificationScreen
import com.example.booksappcompose.presentation.on_boarding.OnBoardingScreen
import com.example.booksappcompose.presentation.person.PersonScreen
import com.example.booksappcompose.presentation.search.SearchScreen

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
            OnBoardingScreen {
                navController.navigate(Screen.Home.route+"/2022/3/false")
            }
        }
        composable(
            route = Screen.Home.route+"/{year}/{month}/{shouldFetchFromRemote}",
            arguments = listOf(
                navArgument(name = "year") {
                    type = NavType.StringType
                    defaultValue = "2022"
                },
                navArgument(name = "month") {
                    type = NavType.StringType
                    defaultValue = "3"
                },
                navArgument(name = "shouldFetchFromRemote") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) {
            HomeScreen(scaffoldState = scaffoldState, sheetState = sheetState, navController = navController)
        }
        composable(route = Screen.Favourites.route) {
            FavouritesScreen(
                scaffoldState = scaffoldState
            )
        }

        composable(route = Screen.Notification.route) {
            NotificationScreen()
        }
        composable(route = Screen.Person.route) {
            PersonScreen()
        }
        composable(route = Screen.Search.route) {
            SearchScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}