package com.example.booksappcompose.presentation.search


sealed class SearchScreenEvent {
    data class OnSearchValueChanged(val value: String): SearchScreenEvent()
    data class OnMoreClicked(val id: Int): SearchScreenEvent()
}
