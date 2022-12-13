package com.example.booksappcompose.presentation.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.booksappcompose.presentation.detail.components.AuthorsCard
import com.example.booksappcompose.presentation.detail.components.ExpandableText
import com.example.booksappcompose.util.UiEvent
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.OnNavigateUp -> {
                    onNavigateUp()
                }
                is UiEvent.Message -> {

                }
                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFA7AAAD)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopDetailScreenSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onBackClick = { viewModel.onEvent(DetailScreenEvent.OnBackClicked) }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 2) + (90).dp)
                .background(
                    color = Color(0xFFF4F3F1)
                )
                .align(Alignment.BottomCenter)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawRoundRect(
                    color = Color(0xFFF4F3F1),
                    cornerRadius = CornerRadius(x = 225.dp.toPx(), y = 35.dp.toPx()),
                    style = Stroke(width = 50.dp.toPx())
                )
            }
            Card(
                modifier = Modifier
                    .offset(y = -(230).dp)
                    .size(
                        width = 210.dp,
                        height = 320.dp
                    )
                    .background(color = Color.Transparent)
                    .clip(RoundedCornerShape(10))
                    .align(Alignment.TopCenter),
                onClick = { },

                ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(state.bookDetail?.cover)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            DetailSection(
                modifier = Modifier
                    .offset(y = 90.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 180.dp)
                    .align(Alignment.TopCenter)
                    .verticalScroll(rememberScrollState())
            )
            BottomBuyBookSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun TopDetailScreenSection(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .background(
                    color = Color(0xFFF4F3F1),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.detail_back),
                tint = Color(0xFFE5C69B)
            )
        }
        IconButton(
            onClick = {},
            modifier = Modifier
                .background(
                    color = Color(0xFFF4F3F1),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(id = R.string.detail_favourite),
                tint = Color(0xFFE5C69B)
            )
        }
    }
}

@Composable
fun BottomBuyBookSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFFF4F3F1),
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(15.dp),
                    color = Color.Gray
                )

        ) {
            Icon(
                imageVector = Icons.Default.AddShoppingCart,
                contentDescription = stringResource(id = R.string.add_to_cart),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(17.dp))
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color(0xFFE5C69B)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.buy_now),
                    color = Color.White,
                    fontSize = 18.sp
                )
                Icon(
                    imageVector = Icons.Default.Forward,
                    contentDescription = stringResource(id = R.string.buy_now_arrow),
                    tint = Color.White
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DetailSection(
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    var maxLines by remember {
        mutableStateOf(1)
    }
    val state = viewModel.state
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.bookDetail?.name ?: "",
                color = Color.Black,
                fontSize = 26.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        maxLines = if (maxLines == 1) {
                            4
                        } else {
                            1
                        }
                    }
            )
            Text(
                text = stringResource(id = R.string.price),
                color = Color(0xFFE5C69B),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(y = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.StarRate,
                contentDescription = stringResource(id = R.string.rating_detail_star),
                tint = Color(0xFFE5C69B),
                modifier = Modifier
                    .size(18.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = state.bookDetail?.rating.toString(),
                color = Color(0xFFE5C69B),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.slash),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = state.bookDetail?.pages.toString() + " pages",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            mainAxisSpacing = 5.dp,
            crossAxisSpacing = 5.dp
        ) {
            state.bookDetail?.authors?.forEach { author ->
                AuthorsCard(
                    onItemClick = {

                    },
                    authorsName = author
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.book_overview),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        ExpandableText(
            text = viewModel.state.bookDetail?.synopsis ?: "",
            modifier = Modifier
                .fillMaxWidth(),
            minimizedMaxLines = 2
        )
    }
}


