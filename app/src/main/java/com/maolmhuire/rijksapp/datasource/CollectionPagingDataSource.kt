package com.maolmhuire.rijksapp.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.repo.CollectionRepo

class CollectionPagingDataSource(private val collectionRepo: CollectionRepo)
    : PagingSource<Int, ArtObject>() {

    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        return try {
            val page = params.key ?: 0
            val response = collectionRepo.getFromCollection(page).body()!!
            LoadResult.Page(
                data = response.artObjects,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}