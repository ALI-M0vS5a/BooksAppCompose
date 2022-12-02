package com.example.booksappcompose.data.remote.dto.book_detail_dto

data class BooksDetailDto(
    val authors: List<String>,
    val book_id: Int,
    val cover: String,
    val name: String,
    val pages: Int,
    val published_date: String,
    val rating: Int,
    val synopsis: String,
    val url: String
)