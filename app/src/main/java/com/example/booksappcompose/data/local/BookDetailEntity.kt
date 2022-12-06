package com.example.booksappcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookDetail")
data class BookDetailEntity(
    val authors: List<String>,
    val book_id: Int,
    val cover: String,
    val name: String,
    val pages: Int,
    val published_date: String,
    val rating: Int,
    val synopsis: String,
    @PrimaryKey val id: Int? = null
)
