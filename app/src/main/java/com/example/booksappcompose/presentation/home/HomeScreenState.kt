package com.example.booksappcompose.presentation.home

import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem


data class HomeScreenState(
    val listOfTop15MostPopularBooks: List<Top15MostPopularBooksItem> = emptyList(),
    val isLoading: Boolean = false,
    val yearSelected: String = "",
    val monthSelected: String = ""
)
