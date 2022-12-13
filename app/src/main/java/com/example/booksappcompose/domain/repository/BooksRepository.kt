package com.example.booksappcompose.domain.repository

import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem
import com.example.booksappcompose.domain.model.book_detail.BooksDetail
import com.example.booksappcompose.domain.model.search.SearchBooksItem
import com.example.booksappcompose.util.Resource
import kotlinx.coroutines.flow.Flow


interface BooksRepository {
    suspend fun getTop15MostPopularBooks(
        fetchFromRemote: Boolean,
        year: String,
        month: String
    ): Flow<Resource<List<Top15MostPopularBooksItem>>>

    suspend fun searchBooksByName(query: String): Flow<Resource<List<SearchBooksItem>>>
    suspend fun getBookDetailById(id: Int, saveToLibrary: Boolean, fromLocal: Boolean): Flow<Resource<BooksDetail>>
    suspend fun isBookAlreadyExistInDb(id: Int): Boolean
    fun savedBooks(): Flow<List<BookDetailEntity>>
    suspend fun deleteBook(book: BookDetailEntity)
    suspend fun restoreDeletedBook(book: BookDetailEntity)
    suspend fun saveBooksToLibrary(book: BookDetailEntity)
}