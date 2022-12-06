package com.example.booksappcompose.util


sealed class UiEvent {
    data class Message(val uiText: UiText): UiEvent()
    data class OnNavigate(val route: String): UiEvent()
    data class ToastMessage(val uiText: UiText): UiEvent()
}