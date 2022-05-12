package com.maolmhuire.rijksapp.model

/**
 * UI Models:
 */
sealed class CollectionItemUI {
    data class ArtObjectUI(val artObject: ArtObject) : CollectionItemUI()
    data class CategoryMakersSeparatorUI(val makersNames: String) : CollectionItemUI()
}

sealed class UIState<out T> where T : Any? {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure(val t: Throwable) : UIState<Nothing>()
}