package com.pemmob.videogame.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.videogame.data.model.GameDto
import com.pemmob.videogame.data.remote.ApiClient
import com.pemmob.videogame.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val games: List<GameDto>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

data class FilterState(
    val selectedGenre: String? = null,
    val minRating: Double = 0.0,
    val selectedYear: String? = null,
) {
    val isActive: Boolean
        get() = (selectedGenre != null) || (minRating > 0.0) || (selectedYear != null)
}

class HomeViewModel(
    private val repository: GameRepository = GameRepository(ApiClient.rawgApi)
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadGames()
    }

    fun onQueryChange(value: String) {
        _query.value = value
        triggerSearch()
    }

    fun setGenreFilter(genre: String?) {
        _filterState.value = _filterState.value.copy(selectedGenre = genre)
        triggerSearch()
    }

    fun setMinRatingFilter(rating: Double) {
        _filterState.value = _filterState.value.copy(minRating = rating)
        triggerSearch()
    }

    fun setYearFilter(year: String?) {
        _filterState.value = _filterState.value.copy(selectedYear = year)
        triggerSearch()
    }

    fun clearFilters() {
        _filterState.value = FilterState()
        triggerSearch()
    }

    private fun triggerSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350)
            loadGames()
        }
    }

    fun loadGames() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val filter = _filterState.value
            val genreSlug = filter.selectedGenre?.let { mapGenreToSlug(it) }
            _uiState.value = try {
                val games = repository.getGames(
                    query = _query.value,
                    genreSlug = genreSlug,
                    year = filter.selectedYear,
                    minRating = filter.minRating
                )
                HomeUiState.Success(games)
            } catch (e: Exception) {
                HomeUiState.Error(e.message ?: "Gagal memuat katalog game")
            }
        }
    }

    private fun mapGenreToSlug(genreName: String): String {
        return when (genreName.uppercase()) {
            "ACTION" -> "action"
            "RPG" -> "role-playing-games-rpg"
            "SHOOTER" -> "shooter"
            "ADVENTURE" -> "adventure"
            "INDIE" -> "indie"
            "STRATEGY" -> "strategy"
            "RACING" -> "racing"
            "SPORTS" -> "sports"
            else -> genreName.lowercase()
        }
    }
}