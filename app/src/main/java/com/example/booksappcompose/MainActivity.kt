package com.example.booksappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booksappcompose.presentation.components.BottomSheetContent
import com.example.booksappcompose.presentation.components.NavigationBar
import com.example.booksappcompose.ui.theme.BooksAppComposeTheme
import com.example.booksappcompose.util.Navigation
import com.example.booksappcompose.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksAppComposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val route = navBackStackEntry?.destination?.route
                val isFullScreen = Screen.isFullScreen(route)
                val scaffoldState = rememberScaffoldState()
                val sheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmStateChange = {
                        it != ModalBottomSheetValue.HalfExpanded
                    }
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ModalBottomSheetLayout(
                        sheetState = sheetState,
                        sheetContent = {
                            BottomSheetContent(
                                bottomSheetState = sheetState,
                                scaffoldState = scaffoldState,
                                navController = navController
                            )
                        },
                        sheetShape = RoundedCornerShape(25.dp),
                        sheetBackgroundColor = Color(0xFFE5C69B),
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Scaffold(
                            bottomBar = {
                                if (!isFullScreen) {
                                    if (route != null) {
                                        NavigationBar(route) { target ->
                                            navController.apply {
                                                if (target == Screen.Home.route) {
                                                    navigate("$target/2022/3/false") {
                                                        restoreState = true
                                                        launchSingleTop = true
                                                        popUpTo(route = Screen.Home.route + "/2022/3/false") {
                                                            saveState = true
                                                        }
                                                    }
                                                } else {
                                                    navigate(target) {
                                                        restoreState = true
                                                        launchSingleTop = true
                                                        popUpTo(route = Screen.Home.route + "/2022/3/false") {
                                                            saveState = true
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            floatingActionButton = {
                                if (!isFullScreen) {
                                    FloatingActionButton(
                                        onClick = { /*TODO*/ },
                                        shape = CircleShape,
                                        backgroundColor = Color(0xFFE5C69B),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .offset(
                                                x = 0.dp,
                                                y = 10.dp
                                            )
                                            .testTag("Floating action button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.ShoppingCart,
                                            contentDescription = stringResource(id = R.string.shopping),
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            floatingActionButtonPosition = FabPosition.Center,
                            isFloatingActionButtonDocked = true,
                            scaffoldState = scaffoldState
                        ) {
                            Navigation(
                                navController = navController,
                                paddingValues = it,
                                scaffoldState = scaffoldState,
                                sheetState = sheetState
                            )
                        }
                    }
                }
            }
        }
    }
}

