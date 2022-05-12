package com.maolmhuire.rijksapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import coil.load
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.databinding.FragCollectionDetailBinding
import com.maolmhuire.rijksapp.model.UIState
import com.maolmhuire.rijksapp.viewmodel.CollectionViewModel
import timber.log.Timber

class CollectionDetailFragment : Fragment()  {

    private val collectionViewModel: CollectionViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var binding: FragCollectionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragCollectionDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectionViewModel.artObjectDetailed.observe(viewLifecycleOwner, Observer {
            when(it) {
                is UIState.Success -> {
                    binding.tvTitle.text = it.data.title
                    binding.ivCollectionDetailImage.load(it.data.webImage?.url)
                }
                is UIState.Failure -> {
                    Timber.w(it.t.message)
                }
            }
        })
    }

}