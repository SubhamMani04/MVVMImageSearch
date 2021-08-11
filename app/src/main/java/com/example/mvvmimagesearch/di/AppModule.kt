package com.example.mvvmimagesearch.di

import com.example.mvvmimagesearch.api.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance() : Retrofit =
            Retrofit.Builder()
                    .baseUrl(ImageApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit) : ImageApi =
            retrofit.create(ImageApi::class.java)
}