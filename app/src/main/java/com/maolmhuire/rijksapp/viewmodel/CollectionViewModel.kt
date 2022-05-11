package com.maolmhuire.rijksapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maolmhuire.rijksapp.datasource.CollectionPagingDataSource
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.repo.CollectionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val collectionRepo: CollectionRepo) :
    ViewModel() {

    private val _flowPagingData: Flow<PagingData<ArtObject>> = Pager(PagingConfig(pageSize = 25)) {
        CollectionPagingDataSource(collectionRepo)
    }.flow
        .cachedIn(viewModelScope)

    fun flowPagingData() : Flow<PagingData<ArtObject>> = _flowPagingData
}