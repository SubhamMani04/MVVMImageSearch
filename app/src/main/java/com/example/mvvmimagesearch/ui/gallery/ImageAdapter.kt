package com.example.mvvmimagesearch.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mvvmimagesearch.R
import com.example.mvvmimagesearch.databinding.ItemImageBinding
import com.example.mvvmimagesearch.models.Image

class ImageAdapter : PagingDataAdapter<Image, ImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null)
            holder.bind(currentItem)
    }

    class ImageViewHolder(private val binding:ItemImageBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(photo:Image){
                    binding.apply {
                        Glide.with(itemView).load(photo.urls.regular)
                                .centerCrop()
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .error(R.drawable.ic_error)
                                .into(ivPhoto)

                        tvPhotoUsername.text = photo.user.username
                    }
                }
    }

    companion object{
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>(){
            override fun areItemsTheSame(oldItem: Image, newItem: Image) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image) =
                oldItem == newItem
        }
    }
}