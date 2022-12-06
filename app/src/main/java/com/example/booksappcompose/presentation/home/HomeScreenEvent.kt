package com.example.booksappcompose.presentation.home


sealed class HomeScreenEvent {
    data class OnYearSelected(val year: String): HomeScreenEvent()
    data class OnMonthSelected(val month: String): HomeScreenEvent()
    object OnViewBooksButtonClicked: HomeScreenEvent()
    object SwipeRefresh: HomeScreenEvent()
    data class OnSearchClick(val route: String): HomeScreenEvent()
}
