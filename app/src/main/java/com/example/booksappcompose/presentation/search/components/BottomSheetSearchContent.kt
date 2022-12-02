package com.example.booksappcompose.presentation.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.booksappcompose.domain.model.book_detail.BooksDetail
import com.example.booksappcompose.presentation.search.SearchScreenViewModel

@ExperimentalMaterialApi
@Composable
fun BottomSheetSearchContent(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    booksDetail: BooksDetail? = null
) {
    val state = viewModel.state
    val context = LocalContext.current
    Box(
        modifier = modifier
    ) {
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
                    booksDetail?.authors?.forEachIndexed { _, s ->
                        Text(
                            text = "$s,",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            maxLines = 1
                        )
                    }
                }
            }
        }
        if(state.isBookDetailLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                color = Color.Gray
            )
        }
    }
}