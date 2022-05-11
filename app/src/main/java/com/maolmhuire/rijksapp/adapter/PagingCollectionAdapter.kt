package com.maolmhuire.rijksapp.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.view.CollectionItemView

class PagingCollectionAdapter : PagingDataAdapter<ArtObject, ArtObjectViewHolder>(
    object : DiffUtil.ItemCallback<ArtObject>() {
        override fun areItemsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        holder.collectionView.setText(getItem(position)?.longTitle ?: "Empty")
        holder.collectionView.setImage(getItem(position)?.webImage?.url ?: "")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtObjectViewHolder {
        return ArtObjectViewHolder(CollectionItemView(parent.context))
    }
}

class ArtObjectViewHolder(val collectionView: CollectionItemView) : RecyclerView.ViewHolder(collectionView) {
    init {
        val res = collectionView.context.resources
        collectionView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            res.getDimension(R.dimen.view_collection_item_height).toInt(),
        )
        val p = res.getDimension(R.dimen.margin).toInt()
        collectionView.setPadding(p, p, p, p)
    }
}