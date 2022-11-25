package com.example.booksappcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "books")
data class Top15MostPopularBooksItemEntity(
    val book_id: String,
    val cover: String,
    val name: String,
    val position: String,
    val rating: Double,
    val url: String,
    @PrimaryKey val id: Int? = null
)
