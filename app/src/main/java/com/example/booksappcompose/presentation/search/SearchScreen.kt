package com.example.booksappcompose.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SearchOff
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
import com.example.booksappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
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
                else -> Unit
            }
        }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetSearchContent(
                booksDetail = viewModel.state.bookDetail,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
            )
        },
        sheetShape = RoundedCornerShape(5.dp),
        sheetBackgroundColor = Color(0xFFE5C69B),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
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
                            onItemClick = {},
                            onMoreClick = {
                                coroutineScope.launch {
                                    if (sheetState.isVisible) {
                                        sheetState.hide()
                                    } else {
                                        viewModel.onEvent(SearchScreenEvent.OnMoreClicked(books.book_id))
                                        sheetState.show()
                                    }
                                }
                            }
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
    color: Color = Color(0xFFE5C69B)
) {
    val state = viewModel.state
    OutlinedTextField(
        value = state.onSearch,
        onValueChange = {
            viewModel.onEvent(SearchScreenEvent.OnSearchValueChanged(it))
        },
        modifier = modifier,
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


@ExperimentalMaterialApi
@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    books: SearchBooksItem,
    onImageClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .clickable { onItemClick() }
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
                books.authors.forEachIndexed { _, s ->
                    Text(
                        text = "$s,",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
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