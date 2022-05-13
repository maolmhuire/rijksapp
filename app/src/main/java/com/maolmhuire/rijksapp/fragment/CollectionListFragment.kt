package com.maolmhuire.rijksapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.adapter.CollectionLoadStateAdapter
import com.maolmhuire.rijksapp.adapter.PagingCollectionAdapter
import com.maolmhuire.rijksapp.databinding.FragCollectionListBinding
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class CollectionListFragment : Fragment() {

    private val collectionViewModel: CollectionViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private lateinit var binding: FragCollectionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragCollectionListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Shared transitions:
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val adapter =
            PagingCollectionAdapter(object : PagingCollectionAdapter.CollectionAdapterListener {
                override fun onItemClick(transitionImage: View, artObject: ArtObject) {
                    ViewCompat.setTransitionName(transitionImage, "item_image")
                    val extras = FragmentNavigatorExtras(transitionImage to "item_image")
                    collectionViewModel.getArtObjectDetailed(artObject)

                    findNavController().navigate(
                        R.id.action_collectionListFragment_to_collectionDetailFragment,
                        bundleOf("url" to artObject.webImage.url),
                        null,
                        extras
                    )
                }
            })

        adapter.addLoadStateListener { loadState ->
            with(binding) {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        pbCollectionListLoading.isVisible = true
                    }
                    loadState.prepend is LoadState.Error
                            || loadState.append is LoadState.Error
                            || loadState.refresh is LoadState.Error -> {
                        pbCollectionListLoading.isVisible = false
                        tvErrorText.isVisible = true
                    }
                    else -> pbCollectionListLoading.isVisible = false
                }
            }
        }

        val concatAdapter = adapter.withLoadStateHeaderAndFooter(
            header = CollectionLoadStateAdapter {
                adapter.retry()
            },
            footer = CollectionLoadStateAdapter {
                adapter.retry()
            }
        )

        binding.rvCollectionList.adapter = concatAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            collectionViewModel.flowPagingData
                .collectLatest { pagingData ->
                    withContext(Dispatchers.Main) {
                        binding.tvErrorText.isVisible = false
                        adapter.submitData(pagingData)
                    }
                }
        }
    }

}