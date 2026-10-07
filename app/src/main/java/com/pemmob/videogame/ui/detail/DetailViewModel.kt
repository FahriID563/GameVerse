package com.pemmob.videogame.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.videogame.data.model.GameDetailDto
import com.pemmob.videogame.data.remote.ApiClient
import com.pemmob.videogame.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val game: GameDetailDto) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

class DetailViewModel(
    private val repository: GameRepository = GameRepository(ApiClient.rawgApi)
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadGame(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            _uiState.value = try {
                DetailUiState.Success(repository.getGameDetail(id))
            } catch (e: Exception) {
                DetailUiState.Error(e.message ?: "Gagal memuat detail game")
            }
        }
    }
}