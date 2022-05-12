package com.maolmhuire.rijksapp.api

import com.maolmhuire.rijksapp.model.ArtworkDetailResponse
import com.maolmhuire.rijksapp.model.CollectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionService {
    @GET("collection")
    suspend fun getFromCollection(
        @Query("p") page: Int, @Query("ps") limit: Int = 25, @Query("s") sort: String = "artist"
    ): Response<CollectionResponse>

    @GET("collection/{id}")
    suspend fun getCollectionDetails(@Path("id") objectNumber: String): Response<ArtworkDetailResponse>
}