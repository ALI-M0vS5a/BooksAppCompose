package com.example.booksappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Top15MostPopularBooksItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BooksDatabase : RoomDatabase() {
    abstract val dao: BooksDao
}