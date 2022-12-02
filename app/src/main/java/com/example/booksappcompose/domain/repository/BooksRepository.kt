package com.example.booksappcompose.domain.repository

import androidx.room.Query
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
    suspend fun getBookDetailById(id: Int): Flow<Resource<BooksDetail>>
}