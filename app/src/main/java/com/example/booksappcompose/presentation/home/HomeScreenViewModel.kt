package com.example.booksappcompose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksappcompose.R
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
    private val repository: BooksRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        val shouldFetchFromRemote = savedStateHandle.get<Boolean>("shouldFetchFromRemote")
        savedStateHandle.get<String>("year")?.let { year ->
            savedStateHandle.get<String>("month")?.let { month ->
                getTop15MostPopularBooks(
                    year = year,
                    month = month,
                    fetchFromRemote = shouldFetchFromRemote ?: false
                )
            }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnYearSelected -> {
                state = state.copy(yearSelected = event.year)
            }
            is HomeScreenEvent.OnMonthSelected -> {
                state = state.copy(monthSelected = event.month)
            }
            is HomeScreenEvent.OnViewBooksButtonClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Message(UiText.StringResource(R.string.from_sheet)))
                }
            }
            is HomeScreenEvent.SwipeRefresh -> {

            }
            is HomeScreenEvent.OnSearchClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnNavigate(event.route))
                }
            }
        }
    }

    private fun getTop15MostPopularBooks(
        fetchFromRemote: Boolean,
        year: String,
        month: String
    ) {
        viewModelScope.launch {
            repository.getTop15MostPopularBooks(fetchFromRemote, year, month).collect { result ->
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
