package com.example.booksappcompose.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.booksappcompose.R
import com.example.booksappcompose.presentation.home.components.Top15MostPopularBooksItem
import com.example.booksappcompose.util.Screen
import com.example.booksappcompose.util.UiEvent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    sheetState: ModalBottomSheetState,
    navController: NavController
) {
    val state = viewModel.state
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val swipeState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Message -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.OnNavigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }
    BackHandler {
        navController.navigate(Screen.OnBoarding.route)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = swipeState,
            onRefresh = {
                viewModel.onEvent(HomeScreenEvent.SwipeRefresh)
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("LazyColumn")
            ) {
                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    TopHomeSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            coroutineScope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                } else {
                                    sheetState.show()
                                }
                            }
                        },
                        onSearchClick = {
                            viewModel.onEvent(HomeScreenEvent.OnSearchClick(Screen.Search.route))
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    if (viewModel.state.isLoading) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            LoadingAnimatedShimmerEffect(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    } else {
                        SeeAllRecommended(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TopHomeSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .testTag("Home menu")
        ) {
            Image(
                imageVector = Icons.Default.List,
                contentDescription = stringResource(id = R.string.home_menu),
                colorFilter = ColorFilter.tint(color = Color(0xFFE5C69B)),
                modifier = Modifier
                    .size(30.dp)
            )
        }

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onSearchClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    colorFilter = ColorFilter.tint(color = Color(0xFFE5C69B)),
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(1.dp))
            Image(
                painter = painterResource(id = R.drawable.home_screen_profile),
                contentDescription = stringResource(id = R.string.search),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

@Composable
fun SeeAllRecommended(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.recommended),
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.see_all),
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .clickable {

                    }
            )
        }
        LazyRow(
            contentPadding = PaddingValues(start = 10.dp, top = 75.dp)
        ) {
            viewModel.state.listOfTop15MostPopularBooks.let { result ->
                items(result.size) { i ->
                    Top15MostPopularBooksItem(
                        books = result[i]
                    )
                }
            }
        }
    }
}

@Composable
fun BestSeller(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

}

@Composable
fun LoadingAnimatedShimmerEffect(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    LazyRow(modifier = modifier, contentPadding = PaddingValues(start = 10.dp, top = 75.dp)) {
        val size = viewModel.state.listOfTop15MostPopularBooks.size
        items(size) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(
                        width = 185.dp,
                        height = 275.dp
                    )
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(25.dp)
                    )
            )
        }
    }
}

