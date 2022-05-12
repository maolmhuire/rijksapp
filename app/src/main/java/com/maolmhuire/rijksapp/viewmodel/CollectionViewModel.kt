package com.maolmhuire.rijksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.maolmhuire.rijksapp.datasource.CollectionPagingDataSource
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.model.ArtObjectDetailed
import com.maolmhuire.rijksapp.model.CollectionItemUI
import com.maolmhuire.rijksapp.model.CollectionItemUI.*
import com.maolmhuire.rijksapp.model.UIState
import com.maolmhuire.rijksapp.repo.CollectionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val collectionRepo: CollectionRepo) :
    ViewModel() {

    val flowPagingData: Flow<PagingData<CollectionItemUI>> = Pager(PagingConfig(pageSize = 25)) {
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

    private val _artObjectDetailed = MutableLiveData<UIState<ArtObjectDetailed>>()
    val artObjectDetailed: LiveData<UIState<ArtObjectDetailed>> = _artObjectDetailed

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _artObjectDetailed.postValue(UIState.Failure(throwable))
    }

    fun getArtObjectDetailed(artObject: ArtObject) {
        _artObjectDetailed.postValue(UIState.Loading)
        viewModelScope.launch(exceptionHandler) {
            val body = collectionRepo.getCollectionDetails(artObject.objectNumber).body()
            _artObjectDetailed.postValue(UIState.Success(requireNotNull(body?.artObject)))
        }
    }

}