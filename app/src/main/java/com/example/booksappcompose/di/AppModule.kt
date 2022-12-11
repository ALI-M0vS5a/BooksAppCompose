package com.example.booksappcompose.di

import android.app.Application
import androidx.room.Room
import com.example.booksappcompose.BuildConfig
import com.example.booksappcompose.data.local.BooksDatabase
import com.example.booksappcompose.data.remote.BooksApi
import com.example.booksappcompose.data.repository.BooksRepositoryImpl
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.domain.use_cases.BooksUseCases
import com.example.booksappcompose.domain.use_cases.GetSavedBooks
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                var request: Request = chain.request()
                val builder: Request.Builder = request.newBuilder()
                    .addHeader(
                        "X-RapidAPI-Key",
                        "b06f3a4fb6msh68e335086ae743cp1d1cfbjsnd1e4bb27c3ce"
                    )
                    .addHeader("X-RapidAPI-Host", "hapi-books.p.rapidapi.com")
                request = builder.build()
                chain.proceed(request)
            })
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideBooksApi(okHttpClient: OkHttpClient): BooksApi {
        return Retrofit.Builder()
            .baseUrl(BooksApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBooksRepository(api: BooksApi, db: BooksDatabase): BooksRepository {
        return BooksRepositoryImpl(api,db)
    }

    @Provides
    @Singleton
    fun provideBooksDatabase(app: Application): BooksDatabase {
        return Room.databaseBuilder(
            app,
            BooksDatabase::class.java,
            "booksdb.db"
        )
            .addMigrations(BooksDatabase.migration1To2)
            .build()
    }

    @Provides
    @Singleton
    fun provideBooksUseCases(repository: BooksRepository): BooksUseCases {
        return BooksUseCases(
            GetSavedBooks = GetSavedBooks(repository)
        )
    }
}