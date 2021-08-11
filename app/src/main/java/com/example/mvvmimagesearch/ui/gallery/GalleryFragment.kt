package com.example.mvvmimagesearch.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mvvmimagesearch.R
import com.example.mvvmimagesearch.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val galleryViewModel by viewModels<GalleryViewModel>()

    private var _binding : FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val imageAdapter = ImageAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = imageAdapter.withLoadStateHeaderAndFooter(
                    header = ImageLoadStateAdapter{imageAdapter.retry()},
                    footer = ImageLoadStateAdapter{imageAdapter.retry()}
            )
        }

        galleryViewModel.photos.observe(viewLifecycleOwner){
            imageAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.recyclerView.scrollToPosition(0)
                    galleryViewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}