package com.example.booksappcompose.data.remote.dto.search_dto

data class ChapterMeasurement(
    val lexile: Int,
    val name: String,
    val number: Int,
    val vocab_words: List<String>
)