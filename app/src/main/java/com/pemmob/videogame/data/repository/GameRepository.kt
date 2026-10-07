package com.pemmob.videogame.data.repository

import com.pemmob.videogame.BuildConfig
import com.pemmob.videogame.data.model.GameDetailDto
import com.pemmob.videogame.data.model.GameDto
import com.pemmob.videogame.data.remote.RawgApiService

class GameRepository(private val api: RawgApiService) {
    suspend fun getGames(
        query: String = "",
        genreSlug: String? = null,
        year: String? = null,
        minRating: Double = 0.0
    ): List<GameDto> {
        val datesParam = year?.let { "$it-01-01,$it-12-31" }
        val response = api.getGames(
            key = BuildConfig.RAWG_API_KEY,
            search = query.ifBlank { null },
            genres = genreSlug,
            dates = datesParam,
            pageSize = 40
        )
        return response.results.filter { game ->
            val matchesRating = game.rating >= minRating
            val matchesYear = year == null || game.released?.startsWith(year) == true
            val matchesGenre = genreSlug == null || game.genres.any {
                it.name.contains(genreSlug, ignoreCase = true) ||
                getGenreSlug(it.name) == genreSlug
            }
            matchesRating && matchesYear && matchesGenre
        }
    }

    private fun getGenreSlug(name: String): String {
        return when (name.lowercase()) {
            "action" -> "action"
            "role-playing-games-rpg", "rpg" -> "role-playing-games-rpg"
            "shooter" -> "shooter"
            "adventure" -> "adventure"
            "indie" -> "indie"
            "strategy" -> "strategy"
            "racing" -> "racing"
            "sports" -> "sports"
            else -> name.lowercase()
        }
    }

    suspend fun getGameDetail(id: Int): GameDetailDto = api.getGameDetail(
        id = id,
        key = BuildConfig.RAWG_API_KEY
    )
}