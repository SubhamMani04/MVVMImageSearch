package com.example.mvvmimagesearch.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
        val id: String,
        val description: String?,
        val urls: ImageUrls,
        val user: ImageUser
) : Parcelable {

    @Parcelize
    data class ImageUrls(
            val raw: String,
            val full: String,
            val regular: String,
            val small: String,
            val thumb: String,
    ) : Parcelable

    @Parcelize
    data class ImageUser(
            val name: String,
            val username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}