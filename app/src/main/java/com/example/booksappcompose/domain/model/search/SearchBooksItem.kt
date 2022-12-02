package com.example.booksappcompose.domain.model.search


data class SearchBooksItem(
    val authors: List<String>,
    val book_id: Int,
    val cover: String,
    val name: String
)
