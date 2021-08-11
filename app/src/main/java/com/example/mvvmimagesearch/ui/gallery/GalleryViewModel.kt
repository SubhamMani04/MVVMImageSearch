package com.example.mvvmimagesearch.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.mvvmimagesearch.data.ImageRepository

class GalleryViewModel @ViewModelInject constructor(
        private val repository: ImageRepository): ViewModel()
{
    companion object{
        private const val DEFAULT_QUERY = "nature"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val photos = currentQuery.switchMap {queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query : String){
        currentQuery.value = query
    }
}