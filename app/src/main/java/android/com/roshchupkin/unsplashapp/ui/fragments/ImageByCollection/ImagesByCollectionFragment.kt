package android.com.roshchupkin.unsplashapp.ui.fragments.ImageByCollection

import android.com.roshchupkin.unsplashapp.R
import android.com.roshchupkin.unsplashapp.databinding.FragmentImageListBinding
import android.com.roshchupkin.unsplashapp.model.ImageDomain
import android.com.roshchupkin.unsplashapp.ui.adapters.ImageAdapter
import android.com.roshchupkin.unsplashapp.ui.adapters.ImageLoadStateAdapter
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImagesByCollectionFragment
@Inject
constructor() : Fragment(R.layout.fragment_image_list), ImageAdapter.Interaction {
    private val imagesByColletionViewModel: ImagesByColletionViewModel by viewModels()
    lateinit var imageAdapter: ImageAdapter

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImageListBinding.bind(view)

        val idCollection: Int = this.arguments?.getInt("itemIdCollection") ?: 0

        imagesByColletionViewModel.getImageList(idCollection).observe(viewLifecycleOwner) {
            imageAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        binding.apply {
            recyclerView.apply {
                imageAdapter = ImageAdapter(this@ImagesByCollectionFragment)
                adapter = imageAdapter.withLoadStateHeaderAndFooter(
                    header = ImageLoadStateAdapter { imageAdapter.retry() },
                    footer = ImageLoadStateAdapter { imageAdapter.retry() }
                )
                buttonRetry.setOnClickListener { imageAdapter.refresh() }
            }
        }

        imageAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible =
                    loadState.source.refresh is LoadState.Error || loadState.source.prepend is LoadState.Loading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewSystemMessage.isVisible = loadState.source.refresh is LoadState.Error
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading

            }
        }

    }

    override fun onItemSelected(position: Int, item: ImageDomain) {
        val bundle = bundleOf("itemID" to item.id)
        findNavController().navigate(R.id.action_imageFragment_to_detailImageFragment, bundle)
    }
}