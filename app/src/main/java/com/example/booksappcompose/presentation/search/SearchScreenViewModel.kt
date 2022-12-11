package com.example.booksappcompose.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksappcompose.R
import com.example.booksappcompose.domain.repository.BooksRepository
import com.example.booksappcompose.util.Resource
import com.example.booksappcompose.util.UiEvent
import com.example.booksappcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    var state by mutableStateOf(SearchScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnSearchValueChanged -> {
                state = state.copy(onSearch = event.value)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1500L)
                    state = state.copy(listOfBooks = emptyList())
                    searchBooksByName(event.value)
                }
            }
            is SearchScreenEvent.OnMoreClicked -> {
                state = state.copy(bookDetail = null)
                getBookDetailById(event.id, false)
            }
            is SearchScreenEvent.OnSaveToLibraryClicked -> {
                viewModelScope.launch {
                    if(state.isBookAlreadySaved) {
                        _eventFlow.emit(UiEvent.ToastMessage(UiText.StringResource(R.string.this_book_already_saved)))
                    } else {
                        getBookDetailById(event.id, true)
                        _eventFlow.emit(UiEvent.Message(UiText.StringResource(R.string.saved)))
                    }
                }
            }
            is SearchScreenEvent.OnDeleteFromLibraryClicked -> {

            }
            is SearchScreenEvent.OnNavigateUp -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnNavigateUp)
                }
            }
        }
    }

    private fun searchBooksByName(query: String) {
        viewModelScope.launch {
            repository.searchBooksByName(query).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        state = state.copy(listOfBooks = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.Message(result.message ?: UiText.unknownError()))
                    }
                }
            }
        }
    }

    private fun getBookDetailById(id: Int, saveToLibrary: Boolean) {
        viewModelScope.launch {
            repository.getBookDetailById(id, saveToLibrary).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isBookDetailLoading = result.isLoading)
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

    fun isBookAlreadySaved(id: Int) {
        viewModelScope.launch {
            val exist = repository.isBookAlreadyExistInDb(id)
            state = if (exist) {
                state.copy(isBookAlreadySaved = true)
            } else {
                state.copy(isBookAlreadySaved = false)
            }
        }
    }
}

