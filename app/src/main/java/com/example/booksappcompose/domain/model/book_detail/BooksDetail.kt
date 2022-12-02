package com.example.booksappcompose.domain.model.book_detail


data class BooksDetail(
    val authors: List<String>,
    val book_id: Int,
    val cover: String,
    val name: String,
    val pages: Int,
    val published_date: String,
    val rating: Int,
    val synopsis: String
)
