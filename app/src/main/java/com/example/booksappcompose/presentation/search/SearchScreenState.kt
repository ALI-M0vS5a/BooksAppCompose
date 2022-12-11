package com.example.booksappcompose.presentation.search

import com.example.booksappcompose.domain.model.book_detail.BooksDetail
import com.example.booksappcompose.domain.model.search.SearchBooksItem


data class SearchScreenState(
    val onSearch: String = "",
    val listOfBooks: List<SearchBooksItem> = emptyList(),
    val isLoading: Boolean = false,
    val bookDetail: BooksDetail? = null,
    val isBookDetailLoading: Boolean = false,
    val isBookAlreadySaved: Boolean = false,
)
