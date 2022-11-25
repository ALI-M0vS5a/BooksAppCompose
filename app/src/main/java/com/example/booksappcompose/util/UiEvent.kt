package com.example.booksappcompose.util

sealed class UiEvent {
    data class Message(val uiText: UiText): UiEvent()
}