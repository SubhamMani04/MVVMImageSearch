package com.example.mvvmimagesearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmimagesearch.api.ImageApi
import com.example.mvvmimagesearch.models.Image
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ImagePagingSource(
        private val imageApi: ImageApi,
        private val query : String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = imageApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results
            
            LoadResult.Page(
                    data = photos,
                    prevKey = if(position == STARTING_PAGE_INDEX) null else position-1,
                    nextKey = if(photos.isEmpty()) null else position+1
            )
        }
        catch (exception : IOException){
            LoadResult.Error(exception)
        }
        catch (exception : HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        TODO("Not yet implemented")
    }
}