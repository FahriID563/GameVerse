package com.pemmob.videogame.data.remote

import com.pemmob.videogame.data.model.GameDetailDto
import com.pemmob.videogame.data.model.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key: String,
        @Query("search") search: String? = null,
        @Query("genres") genres: String? = null,
        @Query("dates") dates: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page_size") pageSize: Int = 40
    ): GameListResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") key: String
    ): GameDetailDto
}