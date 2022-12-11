package com.example.booksappcompose.domain.util


sealed class BooksOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): BooksOrder(orderType)
    class Date(orderType: OrderType): BooksOrder(orderType)
    class Rating(orderType: OrderType): BooksOrder(orderType)

    fun copy(orderType: OrderType): BooksOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Rating -> Rating(orderType)
        }
    }
}
