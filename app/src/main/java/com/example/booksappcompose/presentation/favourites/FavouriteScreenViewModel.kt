package com.example.booksappcompose.presentation.favourites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksappcompose.data.local.BookDetailEntity
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.domain.use_cases.BooksUseCases
import com.example.booksappcompose.domain.util.BooksOrder
import com.example.booksappcompose.domain.util.OrderType
import com.example.booksappcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val repository: BooksRepository,
    private val booksUseCases: BooksUseCases
) : ViewModel() {

    var state by mutableStateOf(FavouriteScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedBook: BookDetailEntity? = null

    private var getBooksJob: Job? = null

    init {
        getSavedBooks(BooksOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.Order -> {
                if (state.booksOrder::class == event.booksOrder::class &&
                    state.booksOrder.orderType == event.booksOrder.orderType
                ) {
                    return
                }
                getSavedBooks(event.booksOrder)
            }
            is FavouritesScreenEvent.DeleteBook -> {
                viewModelScope.launch {
                    repository.deleteBook(event.book)
                    recentlyDeletedBook = event.book
                }
            }
            is FavouritesScreenEvent.RestoreBook -> {
                viewModelScope.launch {
                    repository.restoreDeletedBook(recentlyDeletedBook ?: return@launch)
                    recentlyDeletedBook = null
                }
            }
            is FavouritesScreenEvent.ToggleOrderSection -> {
                state = state.copy(
                    isOrderSectionVisible = !state.isOrderSectionVisible
                )
            }
            is FavouritesScreenEvent.OnItemClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnNavigate(event.route))
                }
            }
        }
    }

    private fun getSavedBooks(booksOrder: BooksOrder) {
        getBooksJob?.cancel()
        getBooksJob = booksUseCases.GetSavedBooks(booksOrder)
            .onEach {
                books ->
                state = state.copy(
                    savedBooks = books,
                    booksOrder = booksOrder
                )
            }
            .launchIn(viewModelScope)
    }
}