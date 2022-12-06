package com.example.booksappcompose.presentation.favourites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.booksappcompose.data.local.BookDetailEntity

@ExperimentalMaterialApi
@Composable
fun FavouritesScreen(
    viewModel: FavouriteScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .padding(10.dp),
           contentPadding = PaddingValues(bottom = 120.dp)
       ) {
           items(state.savedBooks.size) { i ->
               Spacer(modifier = Modifier.height(20.dp))
               SavedBookItem(
                   savedBook = state.savedBooks[i],
                   modifier = Modifier
                       .fillMaxWidth()
               )
           }
       }
    }
}

@ExperimentalMaterialApi
@Composable
fun SavedBookItem(
    modifier: Modifier = Modifier,
    savedBook: BookDetailEntity
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
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
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = savedBook.name,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
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
    }
}