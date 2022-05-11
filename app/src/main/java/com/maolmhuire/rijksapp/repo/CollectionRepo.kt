package com.maolmhuire.rijksapp.repo

import com.maolmhuire.rijksapp.api.CollectionService
import com.maolmhuire.rijksapp.model.CollectionResponse
import retrofit2.Response

class CollectionRepo(private val collectionService: CollectionService) {
    suspend fun getFromCollection(page: Int = 0): Response<CollectionResponse> =
        collectionService.getFromCollection(page = page)

    suspend fun getCollectionDetails(objectNumber: Long) =
        collectionService.getCollectionDetails(objectNumber)
}