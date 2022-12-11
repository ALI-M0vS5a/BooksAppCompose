package com.example.booksappcompose.presentation.favourites

import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.domain.util.BooksOrder


sealed class FavouritesScreenEvent {
    data class Order(val booksOrder: BooksOrder): FavouritesScreenEvent()
    data class DeleteBook(val book: BookDetailEntity): FavouritesScreenEvent()
    object RestoreBook: FavouritesScreenEvent()
    object ToggleOrderSection: FavouritesScreenEvent()
}
