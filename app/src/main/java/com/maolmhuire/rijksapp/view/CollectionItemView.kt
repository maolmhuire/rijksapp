package com.maolmhuire.rijksapp.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
        with(binding) {
            ivCollectionImage.load(url) {
                listener(onStart = {
                    ivCollectionImage.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.image_bg_placeholder)
                    ) },
                    onError = { _, _ ->
                        ivCollectionImage.setImageDrawable(
                            ContextCompat.getDrawable(context, R.drawable.image_bg_error)
                        )
                    }
                )
            }
        }
    }

    fun setText(text: String) {
        binding.tvTitle.text = text
    }

    fun getImageView(): ImageView = binding.ivCollectionImage
}