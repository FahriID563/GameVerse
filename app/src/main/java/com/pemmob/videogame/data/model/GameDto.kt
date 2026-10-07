package com.pemmob.videogame.data.model

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    val results: List<GameDto> = emptyList()
)

data class GameDto(
    val id: Int,
    val name: String,
    val released: String?,
    val rating: Double,
    @SerializedName("background_image") val backgroundImage: String?,
    val genres: List<GenreDto> = emptyList(),
    val platforms: List<PlatformWrapperDto> = emptyList(),
    val metacritic: Int?
)

data class GameDetailDto(
    val id: Int,
    val name: String,
    val released: String?,
    val rating: Double,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("description_raw") val description: String?,
    val genres: List<GenreDto> = emptyList(),
    val platforms: List<PlatformWrapperDto> = emptyList(),
    val metacritic: Int?
)

data class GenreDto(val name: String)

data class PlatformWrapperDto(val platform: PlatformDto?)

data class PlatformDto(val name: String)