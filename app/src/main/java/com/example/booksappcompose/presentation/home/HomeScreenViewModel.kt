package com.example.booksappcompose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class HomeScreenViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        getTop15MostPopularBooks()
    }

    fun onEvent(event: UiEvent) {

    }

    private fun getTop15MostPopularBooks(
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getTop15MostPopularBooks(fetchFromRemote,"2022", "3").collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            listOfTop15MostPopularBooks = result.data ?: emptyList()
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.Message(result.message ?: UiText.unknownError()))
                    }
                }
            }
        }
    }
}