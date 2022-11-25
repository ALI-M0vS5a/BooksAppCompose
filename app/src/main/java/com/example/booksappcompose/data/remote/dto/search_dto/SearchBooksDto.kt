package com.example.booksappcompose.data.remote.dto.search_dto

data class SearchBooksDto(
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)