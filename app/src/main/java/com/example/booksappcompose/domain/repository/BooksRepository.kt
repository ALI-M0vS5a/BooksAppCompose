package com.example.booksappcompose.domain.repository

import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem
import com.example.booksappcompose.util.Resource
import kotlinx.coroutines.flow.Flow


interface BooksRepository {
    suspend fun getTop15MostPopularBooks(
        fetchFromRemote: Boolean,
        year: String,
        month: String
    ): Flow<Resource<List<Top15MostPopularBooksItem>>>
}