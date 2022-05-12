package com.maolmhuire.rijksapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import coil.load
import com.google.android.material.transition.MaterialContainerTransform
import com.maolmhuire.rijksapp.R
import com.maolmhuire.rijksapp.databinding.FragCollectionDetailBinding
import com.maolmhuire.rijksapp.model.UIState
import com.maolmhuire.rijksapp.viewmodel.CollectionViewModel
import timber.log.Timber
import java.util.*

class CollectionDetailFragment : Fragment() {

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
            when (it) {
                is UIState.Loading -> {
                    binding.pbCollectionDetail.isVisible = true
                }
                is UIState.Success -> {
                    val detailsFormat = "%s \n\n%s \n\n%s \n\n%s \n\n%s \n\n%s \n\n%s \n\n%s"
                    val notAvail = getString(R.string.not_available)
                    val yearEarly = it.data.dating?.yearEarly?.toString() ?: notAvail
                    val yearLate = it.data.dating?.yearLate?.toString() ?: notAvail
                    binding.pbCollectionDetail.isVisible = false
                    binding.tvDetails.text = String.format(
                        Locale.UK,
                        detailsFormat,
                        getString(R.string.collection_detail_title, it.data.title),
                        getString(R.string.collection_detail_full_title, it.data.longTitle),
                        getString(
                            R.string.collection_detail_description,
                            it.data.description ?: notAvail
                        ),
                        getString(
                            R.string.collection_detail_maker,
                            it.data.principalMaker ?: notAvail
                        ),
                        getString(
                            R.string.collection_detail_location,
                            it.data.location ?: notAvail
                        ),
                        getString(
                            R.string.collection_detail_object_num,
                            it.data.objectNumber ?: notAvail
                        ),
                        getString(R.string.collection_detail_years, yearEarly, yearLate),
                        getString(
                            R.string.collection_detail_artist_role,
                            it.data.artistRole ?: notAvail
                        )
                    )
                }
                is UIState.Failure -> {
                    binding.pbCollectionDetail.isVisible = false
                    binding.tvDetails.text = getString(R.string.error_message)
                    Timber.w(it.t.message)
                }
            }
        })
    }

}