package com.example.booksappcompose.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.booksappcompose.R
import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem

@Composable
fun Top15MostPopularBooksItem(
    modifier: Modifier = Modifier,
    books: Top15MostPopularBooksItem
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .padding(10.dp)
            .size(
                width = 185.dp,
                height = 275.dp
            )
            .background(
                color = Color(0xFFF4F3F1),
                shape = RoundedCornerShape(25.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = -(75).dp, x = 0.dp)
                    .clip(RoundedCornerShape(20.dp)),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(books.cover)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = books.name,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
                .align(Alignment.BottomCenter)
                .offset(y = -(18).dp, x = 0.dp)
        ) {
            Text(
                text = books.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = books.rating.toString(),
                    modifier = Modifier
                        .size(16.dp),
                    colorFilter = ColorFilter.tint(color = Color(0xFFE8E230))
                )
                Text(
                    text = books.rating.toString(),
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.price),
                    color = Color(0xFFE5C69B),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .background(
                            color = Color(0xFFE5C69B),
                            shape = CircleShape
                        )
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        }
    }
}
