package com.example.booksappcompose.data.mapper

import com.example.booksappcompose.data.local.Top15MostPopularBooksItemEntity
import com.example.booksappcompose.data.remote.dto.Top15MostPopularDto
import com.example.booksappcompose.data.remote.dto.Top15MostPopularDtoItem
import com.example.booksappcompose.domain.model.Top15MostPopularBooks
import com.example.booksappcompose.domain.model.Top15MostPopularBooksItem

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