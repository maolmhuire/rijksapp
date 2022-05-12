package com.maolmhuire.rijksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.maolmhuire.rijksapp.datasource.CollectionPagingDataSource
import com.maolmhuire.rijksapp.model.CollectionItemUI
import com.maolmhuire.rijksapp.model.CollectionItemUI.*
import com.maolmhuire.rijksapp.repo.CollectionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val collectionRepo: CollectionRepo) :
    ViewModel() {

    private val _flowPagingData: Flow<PagingData<CollectionItemUI>> = Pager(PagingConfig(pageSize = 25)) {
        CollectionPagingDataSource(collectionRepo)
    }.flow
        .map { pagingData -> pagingData.map { ArtObjectUI(it) } }
        .map { it.insertSeparators { before, after ->
            if (after == null) {
                return@insertSeparators null
            }
            if (before == null) {
                return@insertSeparators CategoryMakersSeparatorUI(after.artObject.principalOrFirstMaker)
            }
            if (before.artObject.principalOrFirstMaker != after.artObject.principalOrFirstMaker) {
                CategoryMakersSeparatorUI(after.artObject.principalOrFirstMaker)
            } else {
                // no separator
                null
            }
        } }
        .cachedIn(viewModelScope)

    fun flowPagingData() : Flow<PagingData<CollectionItemUI>> = _flowPagingData
}