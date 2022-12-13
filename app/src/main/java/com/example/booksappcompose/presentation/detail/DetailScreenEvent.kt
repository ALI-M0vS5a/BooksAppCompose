package com.example.booksappcompose.presentation.detail


sealed class DetailScreenEvent {
    object OnReadMoreClicked: DetailScreenEvent()
    object OnBackClicked: DetailScreenEvent()
}
