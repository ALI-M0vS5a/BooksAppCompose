package com.example.booksappcompose.presentation.favourites.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booksappcompose.R
import com.example.booksappcompose.domain.util.BooksOrder
import com.example.booksappcompose.domain.util.OrderType
import com.example.booksappcompose.util.TestTags


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    booksOrder: BooksOrder = BooksOrder.Date(OrderType.Descending),
    onOrderChange: (BooksOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                selected = booksOrder is BooksOrder.Title,
                onSelect = { onOrderChange(BooksOrder.Title(booksOrder.orderType)) },
                modifier = Modifier
                    .testTag(TestTags.TITLE_RADIO_BUTTON)
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.date),
                selected = booksOrder is BooksOrder.Date,
                onSelect = { onOrderChange(BooksOrder.Date(booksOrder.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.rating),
                selected = booksOrder is BooksOrder.Rating,
                onSelect = { onOrderChange(BooksOrder.Rating(booksOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.ascending),
                selected = booksOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(booksOrder.copy(OrderType.Ascending))
                },
                modifier = Modifier
                    .testTag(TestTags.ASCENDING_RADIO_BUTTON)
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.descending),
                selected = booksOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(booksOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}