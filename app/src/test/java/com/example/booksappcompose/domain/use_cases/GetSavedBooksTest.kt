package com.example.booksappcompose.domain.use_cases

import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.data.repository.FakeBooksRepository
import com.example.booksappcompose.domain.util.BooksOrder
import com.example.booksappcompose.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class GetSavedBooksTest {

    private lateinit var getSavedBooksF: GetSavedBooks
    private lateinit var fakeBooksRepository: FakeBooksRepository


    @Before
    fun setUp() {
        fakeBooksRepository = FakeBooksRepository()
        getSavedBooksF = GetSavedBooks(fakeBooksRepository)

        val notesToInsert = mutableListOf<BookDetailEntity>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                BookDetailEntity(
                    authors = listOf("$c"),
                    book_id = index,
                    cover = c.toString(),
                    name = c.toString(),
                    pages = index,
                    published_date = c.toString(),
                    rating = index,
                    synopsis = c.toString()
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeBooksRepository.saveBooksToLibrary(it) }
        }
    }


    @Test
    fun `Order notes by title ascending, correct order`() = runTest {
        val notes = getSavedBooksF(BooksOrder.Title(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].name).isLessThan(notes[i+1].name)
        }
    }
    @Test
    fun `Order notes by title descending, correct order`() = runTest {
        val notes = getSavedBooksF(BooksOrder.Title(OrderType.Descending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].name).isGreaterThan(notes[i+1].name)
        }
    }
    @Test
    fun `Order notes by date ascending, correct order`() = runTest {
        val notes = getSavedBooksF(BooksOrder.Date(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].published_date).isLessThan(notes[i+1].published_date)
        }
    }
    @Test
    fun `Order notes by date descending, correct order`() = runTest {
        val notes = getSavedBooksF(BooksOrder.Rating(OrderType.Descending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].published_date).isGreaterThan(notes[i+1].published_date)
        }
    }
    @Test
    fun `Order notes by rating ascending, correct order`() = runTest {
        val notes = getSavedBooksF(BooksOrder.Rating(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].rating).isLessThan(notes[i+1].rating)
        }
    }
    @Test
    fun `Order notes by rating descending, correct order`() = runTest {
        val notes = getSavedBooksF(BooksOrder.Rating(OrderType.Descending)).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].rating).isGreaterThan(notes[i+1].rating)
        }
    }
}

