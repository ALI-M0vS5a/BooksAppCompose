package com.example.booksappcompose.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.util.Resource
import com.example.booksappcompose.util.UiEvent
import com.example.booksappcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: BooksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DetailScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            println("bookId is $bookId")
            getBookDetailById(bookId)
        }
    }

    fun onEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.OnReadMoreClicked -> {

            }
            is DetailScreenEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnNavigateUp)
                }
            }
        }
    }


    private fun getBookDetailById(id: Int) {
        viewModelScope.launch {
            repository.getBookDetailById(
                id = id,
                saveToLibrary = false,
                fromLocal = true
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        state = state.copy(bookDetail = result.data)
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.Message(result.message ?: UiText.unknownError()))
                    }
                }
            }
        }
    }
}