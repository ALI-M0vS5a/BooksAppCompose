package com.example.booksappcompose.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booksappcompose.R
import com.example.booksappcompose.presentation.home.components.Top15MostPopularBooksItem
import com.example.booksappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    sheetState: ModalBottomSheetState
) {
    val state = viewModel.state
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Message -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            SeeAllRecommended(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Color(0xFFE5C69B)
            )
        }
    }
}


@Composable
fun TopHomeSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
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
                onClick = { /*TODO*/ },
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
            contentPadding = PaddingValues(start = 10.dp,top = 75.dp)
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
fun BottomSheetContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bottom Sheet",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}