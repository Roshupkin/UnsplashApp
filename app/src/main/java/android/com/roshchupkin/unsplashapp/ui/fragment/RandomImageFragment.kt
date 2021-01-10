package android.com.roshchupkin.unsplashapp.ui.fragment

import android.com.roshchupkin.unsplashapp.R
import android.com.roshchupkin.unsplashapp.database.entity.RandomImageCacheEntity
import android.com.roshchupkin.unsplashapp.databinding.FragmentRandomPhotoBinding
import android.com.roshchupkin.unsplashapp.ui.adapters.PhotoAdapter
import android.com.roshchupkin.unsplashapp.ui.viewmodule.RandomImageViewModule
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalPagingApi
class RandomImageFragment
@Inject
constructor() : Fragment(R.layout.fragment_random_photo), PhotoAdapter.Interaction {
    private val randomImageViewModule: RandomImageViewModule by viewModels()
    lateinit var photoAdapter: PhotoAdapter

    private var _binding: FragmentRandomPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomImageViewModule.clearAllRandomImage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRandomPhotoBinding.bind(view)

        randomImageViewModule.dataState.observe(viewLifecycleOwner) {
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        binding.apply {
            recyclerView.apply {
                photoAdapter = PhotoAdapter(this@RandomImageFragment)
                adapter = photoAdapter
            }
        }

      /*  photoAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

               *//* // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    photoAdapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }*//*
            }
        }*/


    }


    override fun onItemSelected(position: Int, item: RandomImageCacheEntity) {
       val bundle = bundleOf("itemID" to item.id)
        findNavController().navigate(R.id.action_global_detailImageFragment, bundle)
    }


}
