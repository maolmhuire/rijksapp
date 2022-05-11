package com.maolmhuire.rijksapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.databinding.ViewCollectionItemBinding

class CollectionItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding: ViewCollectionItemBinding  = ViewCollectionItemBinding.bind(
        inflate(context, R.layout.view_collection_item, this)
    )

    fun setImage(url: String) {
        binding.ivCollectionImage.load(url)
    }

    fun setText(text: String) {
        binding.tvTitle.text = text
    }
}