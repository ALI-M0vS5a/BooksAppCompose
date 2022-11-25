package com.example.booksappcompose.data.remote

import com.example.booksappcompose.data.remote.dto.Top15MostPopularDto
import retrofit2.http.GET
import retrofit2.http.Path


interface BooksApi {

    @GET("/month/{year}/{month}")
    suspend fun getTop15MostPopularBooks(
        @Path("year") year: String,
        @Path("month") month: String
    ): Top15MostPopularDto

    companion object {
        const val BASE_URL = "https://hapi-books.p.rapidapi.com/"
    }
}