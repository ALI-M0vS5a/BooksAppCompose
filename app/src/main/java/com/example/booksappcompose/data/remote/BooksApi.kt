package com.example.booksappcompose.data.remote

import com.example.booksappcompose.data.remote.dto.Top15MostPopularDto
import com.example.booksappcompose.data.remote.dto.book_detail_dto.BooksDetailDto
import com.example.booksappcompose.data.remote.dto.search_dto.SearchBooksDto
import retrofit2.http.GET
import retrofit2.http.Path


interface BooksApi {

    @GET("/month/{year}/{month}")
    suspend fun getTop15MostPopularBooks(
        @Path("year") year: String,
        @Path("month") month: String
    ): Top15MostPopularDto

    @GET("/search/{query}")
    suspend fun searchBooksByName(
        @Path("query") query: String
    ): SearchBooksDto

    @GET("/book/{id}")
    suspend fun getBooksDetailById(
        @Path("id") id: Int
    ): BooksDetailDto

    companion object {
        const val BASE_URL = "https://hapi-books.p.rapidapi.com/"
    }
}