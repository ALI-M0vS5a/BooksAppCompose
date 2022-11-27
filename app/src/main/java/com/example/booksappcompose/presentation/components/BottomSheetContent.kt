package com.example.booksappcompose.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.booksappcompose.R
import com.example.booksappcompose.presentation.home.HomeScreenEvent
import com.example.booksappcompose.presentation.home.HomeScreenViewModel
import com.example.booksappcompose.util.Screen
import com.example.booksappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    bottomSheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val state = viewModel.state
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Message -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .align(Alignment.TopCenter)
                .padding(25.dp)
        ) {
            OutlinedTextField(
                value = state.yearSelected,
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.OnYearSelected(it))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_year),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center
                    )
                },
                shape = CircleShape,
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.monthSelected,
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.OnMonthSelected(it))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_month),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center
                    )
                },
                shape = CircleShape,
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        if(bottomSheetState.isVisible) {
                            bottomSheetState.hide()
                            navController.navigate(Screen.Home.route+"/${state.yearSelected}/${state.monthSelected}/true")
                            viewModel.onEvent(HomeScreenEvent.OnViewBooksButtonClicked)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(id = R.string.view_books),
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}