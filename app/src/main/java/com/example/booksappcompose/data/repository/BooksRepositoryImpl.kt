package com.example.booksappcompose.data.repository

import com.example.booksappcompose.R
import com.example.booksappcompose.data.local.BooksDatabase
import com.example.booksappcompose.data.mapper.toTop15MostPopularBooksItem
import com.example.booksappcompose.data.mapper.toTop15MostPopularBooksItemEntity
import com.example.booksappcompose.data.remote.BooksApi
import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.util.Resource
import com.example.booksappcompose.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BooksRepositoryImpl(
    private val api: BooksApi,
    db: BooksDatabase
) : BooksRepository {
    private val dao = db.dao
    override suspend fun getTop15MostPopularBooks(
        fetchFromRemote: Boolean,
        year: String,
        month: String
    ): Flow<Resource<List<Top15MostPopularBooksItem>>> = flow {
        emit(Resource.Loading(isLoading = true))
        val localBooks = dao.getBooks()
        val isDbEmpty = localBooks.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if(shouldJustLoadFromCache) {
            emit(Resource.Loading(isLoading = false))
            emit(Resource.Success(
                data = localBooks.map { it.toTop15MostPopularBooksItem() }
            ))
            return@flow
        }

        val remote = try {
            api.getTop15MostPopularBooks(
                year = year,
                month = month
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = UiText.StringResource(
                        resId = R.string.please_check_your_connection
                    ),
                    data = localBooks.map { it.toTop15MostPopularBooksItem() }
                )
            )
            null
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = (UiText.StringResource(
                        resId = R.string.Oops_something_went_wrong
                    )),
                    data = localBooks.map { it.toTop15MostPopularBooksItem() }
                )
            )
            null
        }
        remote?.let { top15MostPopularBooks ->
            dao.clear()
            dao.insertBooks(
                top15MostPopularBooks.map { it.toTop15MostPopularBooksItemEntity() }
            )
            emit(Resource.Success(
                data = dao.getBooks().map { it.toTop15MostPopularBooksItem() }
            ))
            emit(Resource.Loading(isLoading = false))
        }
    }
}