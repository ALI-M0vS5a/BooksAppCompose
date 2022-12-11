package com.example.booksappcompose.presentation.search


sealed class SearchScreenEvent {
    data class OnSearchValueChanged(val value: String): SearchScreenEvent()
    data class OnMoreClicked(val id: Int): SearchScreenEvent()
    data class OnSaveToLibraryClicked(val id: Int): SearchScreenEvent()
    object OnDeleteFromLibraryClicked: SearchScreenEvent()
    object OnNavigateUp: SearchScreenEvent()
}
