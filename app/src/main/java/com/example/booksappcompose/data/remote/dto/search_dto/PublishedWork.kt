package com.example.booksappcompose.data.remote.dto.search_dto

data class PublishedWork(
    val binding: String,
    val copyright: Int,
    val cover_art_url: String,
    val english_language_learner: Boolean,
    val isbn: String,
    val page_count: Int,
    val published_work_id: Int
)