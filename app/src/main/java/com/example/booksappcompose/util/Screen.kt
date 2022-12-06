package com.example.booksappcompose.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.booksappcompose.R
import com.example.booksappcompose.util.Routes.ROUTE_FAVOURITES
import com.example.booksappcompose.util.Routes.ROUTE_HOME
import com.example.booksappcompose.util.Routes.ROUTE_NOTIFICATION
import com.example.booksappcompose.util.Routes.ROUTE_NULL
import com.example.booksappcompose.util.Routes.ROUTE_ON_BOARDING
import com.example.booksappcompose.util.Routes.ROUTE_PERSON
import com.example.booksappcompose.util.Routes.ROUTE_SEARCH
import com.example.booksappcompose.util.Routes.fullScreenRoutes


object Routes {
    const val ROUTE_ON_BOARDING = "ROUTE_ON_BOARDING"
    const val ROUTE_HOME = "ROUTE_HOME"
    const val ROUTE_FAVOURITES = "ROUTE_FAVOURITES"
    const val ROUTE_NOTIFICATION = "ROUTE_NOTIFICATION"
    const val ROUTE_PERSON = "ROUTE_PERSON"
    const val ROUTE_NULL = "-"
    const val ROUTE_SEARCH = "ROUTE_SEARCH"

    val fullScreenRoutes = listOf(
        ROUTE_ON_BOARDING,
        ROUTE_SEARCH
    )
}

sealed class Screen(
    val route: String,
    var tag: String = route,
    val title: String = "",
    val icon: ImageVector? = null
) {
    object OnBoarding : Screen(route = ROUTE_ON_BOARDING)
    object Home : Screen(route = ROUTE_HOME, icon = Icons.Filled.Home, title = "Home screen")
    object Favourites : Screen(route = ROUTE_FAVOURITES, icon = Icons.Outlined.FavoriteBorder, title = "Favourites screen")
    object Null : Screen(route = ROUTE_NULL)
    object Notification : Screen(route = ROUTE_NOTIFICATION, icon = Icons.Default.Notifications)
    object Person : Screen(route = ROUTE_PERSON, icon = Icons.Default.Person)
    object Search : Screen(route = ROUTE_SEARCH)

    companion object {
        fun isFullScreen(route: String?): Boolean {
            return fullScreenRoutes.contains(route)
        }
    }
}
