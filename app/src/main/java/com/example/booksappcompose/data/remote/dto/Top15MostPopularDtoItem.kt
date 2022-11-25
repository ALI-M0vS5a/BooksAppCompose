package com.example.booksappcompose.data.remote.dto

data class Top15MostPopularDtoItem(
    val book_id: String,
    val cover: String,
    val name: String,
    val position: String,
    val rating: Double,
    val url: String
)