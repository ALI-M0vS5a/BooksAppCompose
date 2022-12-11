package com.example.booksappcompose.domain.util


sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
