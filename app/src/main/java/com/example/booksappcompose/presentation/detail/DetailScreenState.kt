package com.example.booksappcompose.presentation.detail

import com.example.booksappcompose.domain.model.book_detail.BooksDetail


data class DetailScreenState(
    val bookDetail: BooksDetail? = null,
    val isLoading: Boolean = false
)
