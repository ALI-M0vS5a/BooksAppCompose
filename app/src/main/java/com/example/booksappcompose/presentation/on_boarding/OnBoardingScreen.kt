package com.example.booksappcompose.presentation.on_boarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booksappcompose.R

@Composable
fun OnBoardingScreen(
    navigateToHomeScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.books_home_page_cover_3),
                    contentDescription = stringResource(id = R.string.home_page_cover),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            BottomSection(
                modifier = Modifier
                    .fillMaxWidth(),
                navigateToHomeScreen = navigateToHomeScreen
            )
        }
    }
}

@Composable
fun BottomSection(
    modifier: Modifier = Modifier,
    navigateToHomeScreen: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.read_everytime),
            fontSize = 37.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                 .testTag("Read Everytime")
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(id = R.string.buy_and_read_your_favorite),
            fontSize = 24.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(id = R.string.book_with_best_price),
            fontSize = 24.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(65.dp))
        GetStartedButton(
            modifier = Modifier
                .size(
                    width = 300.dp,
                    height = 90.dp
                ),
            onClick = { navigateToHomeScreen() }
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun GetStartedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier
            .testTag("Get Started"),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFE5C69B)
        )
    ) {
        Text(
            text = stringResource(id = R.string.get_started),
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

