package com.example.booksappcompose.presentation.favourites

import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.domain.util.BooksOrder
import com.example.booksappcompose.domain.util.OrderType


data class FavouriteScreenState(
    val savedBooks: List<BookDetailEntity> = emptyList(),
    val booksOrder: BooksOrder = BooksOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
