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
    suspend fun clearBooks()

    @Query("SELECT * FROM books")
    suspend fun getBooks(): List<Top15MostPopularBooksItemEntity>

    /////////////////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookToLibrary(
        bookDetailEntity: BookDetailEntity
    )

    @Query("DELETE FROM bookDetail")
    suspend fun clearLibrary()

    @Query("SELECT * FROM bookDetail")
    suspend fun getBooksInLibrary(): List<BookDetailEntity>

    @Query("SELECT EXISTS(SELECT * FROM bookDetail WHERE book_id = :id)")
    suspend fun isBookAlreadyExist(id: Int): Boolean

}