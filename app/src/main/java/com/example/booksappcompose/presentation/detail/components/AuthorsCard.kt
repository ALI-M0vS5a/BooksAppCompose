package com.example.booksappcompose.presentation.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@Composable
fun AuthorsCard(
    modifier: Modifier = Modifier,
    authorsName: String = "",
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(50.dp),
        onClick = onItemClick,
        backgroundColor = Color(0xFFE5C69B),
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp
    ) {
        Box {
            Text(
                text = authorsName,
                color = Color(0xFFB74F18),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
            )
        }
    }
}