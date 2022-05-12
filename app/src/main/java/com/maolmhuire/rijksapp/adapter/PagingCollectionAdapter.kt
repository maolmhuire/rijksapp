package com.maolmhuire.rijksapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.model.CollectionItemUI
import com.maolmhuire.rijksapp.model.CollectionItemUI.*
import com.maolmhuire.rijksapp.view.CollectionItemView

class PagingCollectionAdapter(private val listener: CollectionAdapterListener)
    : PagingDataAdapter<CollectionItemUI, CollectionItemViewHolder>(
    object : DiffUtil.ItemCallback<CollectionItemUI>() {
        override fun areItemsTheSame(oldItem: CollectionItemUI, newItem: CollectionItemUI): Boolean {
            return (oldItem is ArtObjectUI && newItem is ArtObjectUI &&
                    oldItem.artObject.id == newItem.artObject.id)
        }

        override fun areContentsTheSame(oldItem: CollectionItemUI, newItem: CollectionItemUI): Boolean {
            return (oldItem is ArtObjectUI && newItem is ArtObjectUI &&
                    oldItem.artObject.longTitle == newItem.artObject.longTitle)
        }
    }
) {

    override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
        val item = checkNotNull(getItem(position))
        if (item is ArtObjectUI) {
            with(holder as ArtObjectViewHolder) {
                collectionView.setText(item.artObject.longTitle)
                collectionView.setImage(item.artObject.webImage.url)
                collectionView.setOnClickListener { listener.onItemClick(item.artObject) }
            }
        } else if (item is CategoryMakersSeparatorUI) {
            (holder as CategoryMakersSeparatorViewHolder).tvMakersSeparatorTitle.text =
                item.makersNames
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionItemViewHolder {
        return if (viewType == R.layout.view_collection_item) {
            ArtObjectViewHolder(CollectionItemView(parent.context))
        } else {
            with(parent) {
                CategoryMakersSeparatorViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.view_makers_seperator, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(checkNotNull(getItem(position))) {
            is ArtObjectUI -> R.layout.view_collection_item
            is CategoryMakersSeparatorUI -> R.layout.view_makers_seperator
        }
    }

    interface CollectionAdapterListener {
        fun onItemClick(artObject: ArtObject)
    }
}

open class CollectionItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

class ArtObjectViewHolder(val collectionView: CollectionItemView)
    : CollectionItemViewHolder(collectionView) {
    init {
        with(collectionView) {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                resources.getDimension(R.dimen.view_collection_item_height).toInt(),
            )
            val p = resources.getDimension(R.dimen.margin).toInt()
            setPadding(p, p, p, p)
            isClickable = true
            background = ContextCompat.getDrawable(context, R.drawable.bg_collection_item_view)
        }
    }
}

class CategoryMakersSeparatorViewHolder(separatorView: View)
    : CollectionItemViewHolder(separatorView) {
    val tvMakersSeparatorTitle = separatorView.findViewById<TextView>(R.id.tv_makers_title)
}