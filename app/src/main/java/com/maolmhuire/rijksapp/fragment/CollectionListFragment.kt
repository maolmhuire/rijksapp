package com.maolmhuire.rijksapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.adapter.PagingCollectionAdapter
import com.maolmhuire.rijksapp.databinding.FragCollectionListBinding
import com.maolmhuire.rijksapp.model.ArtObject
import com.maolmhuire.rijksapp.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        val adapter = PagingCollectionAdapter(object : PagingCollectionAdapter.CollectionAdapterListener {
            override fun onItemClick(artObject: ArtObject) {
                findNavController().navigate(R.id.action_collectionListFragment_to_collectionDetailFragment)
            }
        })
        binding.rvCollectionList.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            collectionViewModel.flowPagingData()
                .catch { it.printStackTrace() }
                .collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

}