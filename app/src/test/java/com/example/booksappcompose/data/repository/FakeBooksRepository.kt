package com.example.booksappcompose.data.repository

import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem
import com.example.booksappcompose.domain.model.book_detail.BooksDetail
import com.example.booksappcompose.domain.model.search.SearchBooksItem
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeBooksRepository: BooksRepository {

    private val books = mutableListOf<BookDetailEntity>()
    private val popularBooks = mutableListOf<Top15MostPopularBooksItem>()
    private val searchBooks = mutableListOf<SearchBooksItem>()


    override suspend fun getTop15MostPopularBooks(
        fetchFromRemote: Boolean,
        year: String,
        month: String
    ): Flow<Resource<List<Top15MostPopularBooksItem>>> {
        return flow { emit(Resource.Success(data = popularBooks)) }
    }

    override suspend fun searchBooksByName(query: String): Flow<Resource<List<SearchBooksItem>>> {
        return flow { emit(Resource.Success(data = searchBooks)) }
    }

    override suspend fun getBookDetailById(
        id: Int,
        saveToLibrary: Boolean
    ): Flow<Resource<BooksDetail>> {
        TODO("Not yet implemented")
    }

    override suspend fun isBookAlreadyExistInDb(id: Int): Boolean {
        return true
    }

    override  fun savedBooks(): Flow<List<BookDetailEntity>> {
        return flow { emit(books) }
    }

    override suspend fun deleteBook(book: BookDetailEntity) {
        books.add(book)
    }

    override suspend fun restoreDeletedBook(book: BookDetailEntity) {
        books.remove(book)
    }

    override suspend fun saveBooksToLibrary(book: BookDetailEntity) {
        books.add(book)
    }
}