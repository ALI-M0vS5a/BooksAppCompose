package com.example.booksappcompose.presentation.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.booksappcompose.R
import com.example.booksappcompose.domain.model.book_detail.BooksDetail
import com.example.booksappcompose.presentation.search.SearchScreenViewModel

@ExperimentalMaterialApi
@Composable
fun BottomSheetSearchContent(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    booksDetail: BooksDetail? = null,
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onSaveTestTag: String
) {
    val state = viewModel.state
    val context = LocalContext.current
    Box(
        modifier = modifier
    ) {
        if (state.isBookDetailLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                color = Color.Gray
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                            .background(color = Color(0xFFE5C69B))
                            .clip(RoundedCornerShape(10)),
                        onClick = { },

                        ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context)
                                    .data(booksDetail?.cover)
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = booksDetail?.name,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = booksDetail?.name ?: "",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            booksDetail?.authors?.forEachIndexed { _, s ->
                                Text(
                                    text = "$s, ",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.Black
                        )
                        .height(1.dp)
                )
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier
                        .clickable { onDeleteClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_from_library),
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(id = R.string.delete_from_library),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .clickable { onSaveClick() }
                        .testTag(onSaveTestTag),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(id = R.string.save_to_library),
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(id = R.string.save_to_library),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            }
        }
    }
}