package com.example.booksappcompose.presentation.favourites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    var state by mutableStateOf(FavouriteScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getSavedBooks()
    }

    private fun getSavedBooks() {
        viewModelScope.launch {
            state = state.copy(savedBooks = repository.savedBooks())
        }
    }
}