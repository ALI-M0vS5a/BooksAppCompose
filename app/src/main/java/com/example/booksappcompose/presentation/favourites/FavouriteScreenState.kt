package com.example.booksappcompose.presentation.favourites

import com.example.booksappcompose.data.local.BookDetailEntity


data class FavouriteScreenState(
    val savedBooks: List<BookDetailEntity> = emptyList()
)
