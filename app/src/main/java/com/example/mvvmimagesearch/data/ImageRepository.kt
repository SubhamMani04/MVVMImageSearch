package com.example.mvvmimagesearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mvvmimagesearch.api.ImageApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val imageApi: ImageApi){

    fun getSearchResults(query : String) =
            Pager(
                    config = PagingConfig(
                            pageSize = 20,
                            maxSize = 100,
                            enablePlaceholders = false
                    ),
                    pagingSourceFactory = {ImagePagingSource(imageApi,query)}
            ).liveData
}