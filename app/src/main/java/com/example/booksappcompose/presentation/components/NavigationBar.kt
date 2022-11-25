package com.example.booksappcompose.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.booksappcompose.util.Screen


@Composable
fun NavigationBar(
    route: String,
    onRouteSelected: (targetRoute: String) -> Unit
) {
    val tabs = remember {
        listOf(
            Screen.Home,
            Screen.Favourites,
            Screen.Null,
            Screen.Notification,
            Screen.Person
        )
    }

    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        tabs.forEachIndexed { index, screen ->
            val targetRoute = screen.route
            val selected = route.contains(targetRoute)
            val lineLength = animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 400
                )
            )
            when(index) {
                2 -> {
                    BottomNavigationItem(
                        icon = {},
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                }
                else -> {
                    BottomNavigationItem(
                        icon = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 20.dp)
                                    .drawBehind {
                                        if (lineLength.value > 0f) {
                                            drawLine(
                                                color = if (selected) Color(0xFFE5C69B) else Color.White,
                                                start = Offset(
                                                    size.width / 2f - lineLength.value * 1.dp.toPx(),
                                                    size.height
                                                ),
                                                end = Offset(
                                                    size.width / 2f + lineLength.value * 1.dp.toPx(),
                                                    size.height
                                                ),
                                                cap = StrokeCap.Round,
                                                strokeWidth = 4.dp.toPx()
                                            )
                                        }
                                    }
                            ) {
                                screen.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = screen.title,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .size(30.dp),
                                        tint = if(selected) Color(0xFFE5C69B) else Color.Gray
                                    )
                                }
                            }
                        },
                        selected = selected,
                        onClick = { onRouteSelected(targetRoute) },
                        selectedContentColor = Color(0xFFE5C69B),
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    }
}