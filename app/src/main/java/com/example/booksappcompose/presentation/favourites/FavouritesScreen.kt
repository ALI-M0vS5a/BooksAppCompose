package com.example.booksappcompose.presentation.favourites

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.booksappcompose.R
import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.presentation.favourites.components.OrderSection
import com.example.booksappcompose.util.Screen
import com.example.booksappcompose.util.TestTags
import com.example.booksappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@ExperimentalMaterialApi
@Composable
fun FavouritesScreen(
    viewModel: FavouriteScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    navigateToDetailScreen: (String) -> Unit = {}
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.OnNavigate -> {
                    navigateToDetailScreen(event.route)
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.your_library),
                        style = MaterialTheme.typography.h4,
                        color = Color.Black
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FavouritesScreenEvent.ToggleOrderSection)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = stringResource(id = R.string.sort)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .testTag(TestTags.ORDER_SECTION),
                        onOrderChange = {
                            viewModel.onEvent(FavouritesScreenEvent.Order(it))
                        },
                        booksOrder = state.booksOrder
                    )
                }
            }
            items(state.savedBooks) { book ->
                Spacer(modifier = Modifier.height(20.dp))
                SavedBookItem(
                    savedBook = book,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onUnsavedClick = {
                        viewModel.onEvent(FavouritesScreenEvent.DeleteBook(book))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(FavouritesScreenEvent.RestoreBook)
                            }
                        }
                    },
                    onItemClick = {
                        viewModel.onEvent(FavouritesScreenEvent.OnItemClick(Screen.Detail.route+"/${book.book_id}"))
                    }
                )
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun SavedBookItem(
    modifier: Modifier = Modifier,
    savedBook: BookDetailEntity,
    onUnsavedClick: () -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .testTag(TestTags.SAVED_BOOK_ITEM)
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .size(
                        width = 75.dp,
                        height = 110.dp
                    )
                    .background(color = Color(0xFFE5C69B))
                    .clip(RoundedCornerShape(10)),
                onClick = { },

                ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(savedBook.cover)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = savedBook.name,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {

                Text(
                    text = savedBook.name,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )


                Row(modifier = Modifier.fillMaxWidth()) {
                    savedBook.authors.forEachIndexed { _, s ->
                        Text(
                            text = "$s, ",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        Text(
                            text = savedBook.rating.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = savedBook.rating.toString(),
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(14.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                    Text(
                        text = "(${savedBook.pages})",
                        fontSize = 12.sp
                    )
                }
            }
        }
        OutlinedButton(
            onClick = onUnsavedClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE5C69B)
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(
                    width = 100.dp,
                    height = 35.dp
                )
                .testTag(TestTags.SAVED_BOOK_UNSAVED)
        ) {
            Text(
                text = stringResource(id = R.string.unsave),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

