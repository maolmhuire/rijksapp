package com.maolmhuire.rijksapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.transition.MaterialContainerTransform
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.databinding.FragCollectionDetailBinding
import com.maolmhuire.rijksapp.model.UIState
import com.maolmhuire.rijksapp.viewmodel.CollectionViewModel
import timber.log.Timber

class CollectionDetailFragment : Fragment()  {

    private val collectionViewModel: CollectionViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var binding: FragCollectionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500
        }
    }

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
        ViewCompat.setTransitionName(binding.ivCollectionDetailImage, "item_image")
        if (arguments?.containsKey("url") == true) {
            binding.ivCollectionDetailImage.load(arguments?.get("url") ?: "")
        }
        collectionViewModel.artObjectDetailed.observe(viewLifecycleOwner, Observer {
            when(it) {
                is UIState.Success -> {
                    binding.tvTitle.text = it.data.title
                }
                is UIState.Failure -> {
                    Timber.w(it.t.message)
                }
            }
        })
    }

}