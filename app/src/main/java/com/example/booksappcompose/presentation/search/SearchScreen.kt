package com.example.booksappcompose.presentation.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.booksappcompose.domain.model.search.SearchBooksItem
import com.example.booksappcompose.presentation.search.components.BottomSheetSearchContent
import com.example.booksappcompose.util.TestTags
import com.example.booksappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        }
    )
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Message -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.ToastMessage -> {
                    Toast.makeText(
                        context,
                        event.uiText.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEvent.OnNavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }
    var bookId by remember {
        mutableStateOf(0)
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetSearchContent(
                booksDetail = viewModel.state.bookDetail,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                onSaveClick = {
                    if (sheetState.isVisible) {
                        coroutineScope.launch {
                            viewModel.onEvent(SearchScreenEvent.OnSaveToLibraryClicked(bookId))
                            if (!viewModel.state.isBookAlreadySaved) {
                                sheetState.hide()
                            }
                        }
                    }
                },
                onSaveTestTag = TestTags.SAVE_BOOK
            )
        },
        sheetShape = RoundedCornerShape(5.dp),
        sheetBackgroundColor = Color(0xFFE5C69B),
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.MODAL_BOTTOM_SHEET)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, end = 15.dp),
                    testTag = TestTags.SEARCH_TEXT_FIELD
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(viewModel.state.listOfBooks.size) { i ->
                        val books = viewModel.state.listOfBooks[i]
                        SearchResultItem(
                            onImageClick = {},
                            books = books,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            onItemClick = {
                                coroutineScope.launch {
                                    if (sheetState.isVisible) {
                                        sheetState.hide()
                                    } else {
                                        viewModel.onEvent(SearchScreenEvent.OnMoreClicked(books.book_id))
                                        bookId = books.book_id
                                        viewModel.isBookAlreadySaved(books.book_id)
                                        sheetState.show()
                                    }
                                }
                            },
                            onMoreClick = {
                                coroutineScope.launch {
                                    if (sheetState.isVisible) {
                                        sheetState.hide()
                                    } else {
                                        viewModel.onEvent(SearchScreenEvent.OnMoreClicked(books.book_id))
                                        bookId = books.book_id
                                        viewModel.isBookAlreadySaved(books.book_id)
                                        sheetState.show()
                                    }
                                }
                            },
                            testTag = TestTags.SEARCH_ITEM

                        )
                    }
                }
            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color(0xFFE5C69B)
                )
            }
            if (viewModel.state.onSearch.isEmpty()) {
                Image(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = stringResource(id = R.string.empty_list),
                    modifier = Modifier
                        .size(96.dp)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color(0xFFE5C69B)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    color: Color = Color(0xFFE5C69B),
    testTag: String = ""
) {
    val state = viewModel.state
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(SearchScreenEvent.OnNavigateUp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.arrow_back),
                tint = Color.Gray
            )
        }
        OutlinedTextField(
            value = state.onSearch,
            onValueChange = {
                viewModel.onEvent(SearchScreenEvent.OnSearchValueChanged(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Light
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = color,
                focusedBorderColor = Color(0xFFB74F18),
                unfocusedBorderColor = color
            )
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    books: SearchBooksItem,
    onImageClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    testTag: String
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .clickable { onItemClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .size(
                        width = 60.dp,
                        height = 90.dp
                    )
                    .clip(RoundedCornerShape(10)),
                onClick = { onImageClick() },

                ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(books.cover)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = books.name,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = books.name,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    books.authors.forEachIndexed { _, s ->
                        Text(
                            text = "$s, ",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.FileDownload,
                    contentDescription = stringResource(id = R.string.download),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_vert),
                    tint = Color.Black,
                    modifier = Modifier
                        .clickable { onMoreClick() }
                )
            }
        }
    }
}