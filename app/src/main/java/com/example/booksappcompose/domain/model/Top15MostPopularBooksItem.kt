package com.example.booksappcompose.domain.model


data class Top15MostPopularBooksItem(
    val book_id: String,
    val cover: String,
    val name: String,
    val position: String,
    val rating: Double,
    val url: String
)
