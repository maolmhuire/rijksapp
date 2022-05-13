package com.maolmhuire.rijksapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.databinding.ViewCollectionLoadingStateBinding

class CollectionLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CollectionLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CollectionLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CollectionLoadStateViewHolder(
        ViewCollectionLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}

class CollectionLoadStateViewHolder(
    private val binding: ViewCollectionLoadingStateBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        with(binding) {
            if (loadState is LoadState.Error) {
                textViewError.text = textViewError.resources.getString(R.string.error_message)
            }
            pbLoadingState.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState !is LoadState.Loading
            textViewError.isVisible = loadState !is LoadState.Loading
            buttonRetry.setOnClickListener {
                retry()
            }
            pbLoadingState.isVisible = true
        }
    }
}