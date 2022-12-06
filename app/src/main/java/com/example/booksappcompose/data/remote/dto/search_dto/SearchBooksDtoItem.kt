package com.example.booksappcompose.data.remote.dto.search_dto

data class SearchBooksDtoItem(
    val authors: List<String>,
    val book_id: Int,
    val cover: String,
    val created_editions: Int,
    val name: String,
    val rating: Double,
    val url: String,
    val year: Int
)