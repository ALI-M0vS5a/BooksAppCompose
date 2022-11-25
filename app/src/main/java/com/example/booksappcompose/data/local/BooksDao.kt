package com.example.booksappcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(
        top15MostPopularBooksItemEntities: List<Top15MostPopularBooksItemEntity>
    )
    @Query("DELETE FROM books")
    suspend fun clear()

    @Query("SELECT * FROM books")
    suspend fun getBooks(): List<Top15MostPopularBooksItemEntity>

}