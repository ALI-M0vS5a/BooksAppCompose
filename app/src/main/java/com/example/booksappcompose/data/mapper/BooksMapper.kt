package com.example.booksappcompose.data.mapper

import com.example.booksappcompose.data.local.Top15MostPopularBooksItemEntity
import com.example.booksappcompose.data.remote.dto.Top15MostPopularDto
import com.example.booksappcompose.data.remote.dto.Top15MostPopularDtoItem
import com.example.booksappcompose.data.remote.dto.book_detail_dto.BooksDetailDto
import com.example.booksappcompose.data.remote.dto.search_dto.SearchBooksDto
import com.example.booksappcompose.data.remote.dto.search_dto.SearchBooksDtoItem
import com.example.booksappcompose.domain.model.Top15MostPopularBooks
import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem
import com.example.booksappcompose.domain.model.book_detail.BooksDetail
import com.example.booksappcompose.domain.model.search.SearchBooks
import com.example.booksappcompose.domain.model.search.SearchBooksItem

fun Top15MostPopularDto.toTop15MostPopularBooks(): Top15MostPopularBooks {
    return Top15MostPopularBooks()
}
fun Top15MostPopularDtoItem.toTop15MostPopularBooksItem(): Top15MostPopularBooksItem {
    return Top15MostPopularBooksItem(
        book_id = book_id,
        cover = cover,
        name = name,
        position = position,
        rating = rating,
        url = url
    )
}
fun Top15MostPopularBooksItemEntity.toTop15MostPopularBooksItem(): Top15MostPopularBooksItem {
    return Top15MostPopularBooksItem(
        book_id = book_id,
        cover = cover,
        name = name,
        position = position,
        rating = rating,
        url = url
    )
}
fun Top15MostPopularDtoItem.toTop15MostPopularBooksItemEntity(): Top15MostPopularBooksItemEntity {
    return Top15MostPopularBooksItemEntity(
        book_id = book_id,
        cover = cover,
        name = name,
        position = position,
        rating = rating,
        url = url
    )
}

fun SearchBooksDtoItem.toSearchBooksItem(): SearchBooksItem {
    return SearchBooksItem(
        authors = authors,
        book_id = book_id,
        cover = cover,
        name = name
    )
}

fun BooksDetailDto.toBookDetail(): BooksDetail {
    return BooksDetail(
        authors = authors,
        book_id = book_id,
        cover = cover,
        name = name,
        pages = pages,
        published_date = published_date,
        rating = rating,
        synopsis = synopsis
    )
}