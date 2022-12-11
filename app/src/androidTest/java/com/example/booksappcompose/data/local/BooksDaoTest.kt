package com.example.booksappcompose.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BooksDaoTest {

    private lateinit var booksDao: BooksDao
    private lateinit var db: BooksDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BooksDatabase::class.java
        ).build()
        booksDao = db.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertBooks() = runTest {
        val top15MostPopularBooksItemEntity = listOf(
            Top15MostPopularBooksItemEntity(
                "58283080",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1627068858i/58283080.jpg",
                "Hook, Line, and Sinker (Bellinger Sisters, #2)",
                "1",
                4.04,
                "https://www.goodreads.com/book/show/58283080-hook-line-and-sinker",
                1
            ),
            Top15MostPopularBooksItemEntity(
                "58371432",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1624553583i/58371432.jpg",
                "The Book of Cold Cases",
                "3",
                3.82,
                "https://www.goodreads.com/book/show/58371432-the-book-of-cold-cases",
                2
            )
        )
        booksDao.insertBooks(top15MostPopularBooksItemEntity)

        val allBooks = booksDao.getBooks()

        assertThat(allBooks).isEqualTo(top15MostPopularBooksItemEntity)
    }
    @Test
    fun clearBooks() = runTest {
        val top15MostPopularBooksItemEntity = listOf(
            Top15MostPopularBooksItemEntity(
                "58283080",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1627068858i/58283080.jpg",
                "Hook, Line, and Sinker (Bellinger Sisters, #2)",
                "1",
                4.04,
                "https://www.goodreads.com/book/show/58283080-hook-line-and-sinker",
                1
            ),
            Top15MostPopularBooksItemEntity(
                "58371432",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1624553583i/58371432.jpg",
                "The Book of Cold Cases",
                "3",
                3.82,
                "https://www.goodreads.com/book/show/58371432-the-book-of-cold-cases",
                2
            )
        )
        booksDao.insertBooks(top15MostPopularBooksItemEntity)
        booksDao.clearBooks()

        val allBooks = booksDao.getBooks()

        assertThat(allBooks).isNotEqualTo(top15MostPopularBooksItemEntity)
    }

    @Test
    @Throws(Exception::class)
    fun saveBookToLibrary() = runTest {
        val bookDetailEntity = BookDetailEntity(
            listOf(
                "J.K. Rowling"
            ),
            72193,
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX318_SY475_.jpg",
            "Harry Potter and the Philosopher's Stone (Harry Potter, #1)",
            223,
            "published_date",
            4,
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A ",
            1
        )
        booksDao.saveBookToLibrary(bookDetailEntity)

        booksDao.getBooksInLibrary().map {
            assertThat(it).contains(bookDetailEntity)
        }
    }

    @Test
    fun clearLibrary() = runTest {
        val bookDetailEntity = BookDetailEntity(
            listOf(
                "J.K. Rowling"
            ),
            72193,
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX318_SY475_.jpg",
            "Harry Potter and the Philosopher's Stone (Harry Potter, #1)",
            223,
            "published_date",
            4,
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A ",
            1
        )
        booksDao.saveBookToLibrary(bookDetailEntity)
        booksDao.clearLibrary()

        booksDao.getBooksInLibrary().map {
            assertThat(it).doesNotContain(bookDetailEntity)
        }
    }

    @Test
    fun isBookAlreadyExist() = runTest {
        val bookDetailEntity = BookDetailEntity(
            listOf(
                "J.K. Rowling"
            ),
            72193,
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX318_SY475_.jpg",
            "Harry Potter and the Philosopher's Stone (Harry Potter, #1)",
            223,
            "published_date",
            4,
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A ",
            1
        )
        booksDao.saveBookToLibrary(bookDetailEntity)

        val byBookId = booksDao.isBookAlreadyExist(72193)

        assertThat(byBookId).isTrue()
    }

    @Test
    fun deleteBookFromLibrary() = runTest {
        val bookDetailEntity = BookDetailEntity(
            listOf(
                "J.K. Rowling"
            ),
            72193,
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX318_SY475_.jpg",
            "Harry Potter and the Philosopher's Stone (Harry Potter, #1)",
            223,
            "published_date",
            4,
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A ",
            1
        )
        booksDao.saveBookToLibrary(bookDetailEntity)

        booksDao.getBooksInLibrary().map {
            assertThat(it).contains(bookDetailEntity)

            val deleteBook = booksDao.deleteBookFromLibrary(72193)

            assertThat(it).doesNotContain(deleteBook)
        }

    }

    @Test
    fun restoreDeletedBookToLibrary() = runTest {
        val bookDetailEntity = BookDetailEntity(
            listOf(
                "J.K. Rowling"
            ),
            72193,
            "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1170803558l/72193._SX318_SY475_.jpg",
            "Harry Potter and the Philosopher's Stone (Harry Potter, #1)",
            223,
            "published_date",
            4,
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A ",
            1
        )
        booksDao.saveBookToLibrary(bookDetailEntity)
        booksDao.getBooksInLibrary().map {
            assertThat(it).contains(bookDetailEntity)

            val deleteBook = booksDao.deleteBookFromLibrary(72193)
            assertThat(it).doesNotContain(deleteBook)
        }


        booksDao.restoreBookToLibrary(bookDetailEntity)
        booksDao.getBooksInLibrary().map {
            assertThat(it).contains(bookDetailEntity)
        }

    }

}