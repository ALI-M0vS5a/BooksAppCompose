package com.example.booksappcompose.domain.use_cases

import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.domain.util.BooksOrder
import com.example.booksappcompose.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetSavedBooks(
    private val repository: BooksRepository
) {
    operator fun invoke(
        booksOrder: BooksOrder = BooksOrder.Date(OrderType.Descending)
    ): Flow<List<BookDetailEntity>> {
        return repository.savedBooks().map { books ->
            when (booksOrder.orderType) {
                is OrderType.Ascending -> {
                    when (booksOrder) {
                        is BooksOrder.Title -> books.sortedBy { it.name.lowercase() }
                        is BooksOrder.Date -> books.sortedBy { it.published_date }
                        is BooksOrder.Rating -> books.sortedBy { it.rating }
                    }
                }
                is OrderType.Descending -> {
                    when (booksOrder) {
                        is BooksOrder.Title -> books.sortedByDescending { it.name.lowercase() }
                        is BooksOrder.Date -> books.sortedByDescending { it.published_date }
                        is BooksOrder.Rating -> books.sortedByDescending { it.rating }
                    }
                }
            }
        }
    }
}

