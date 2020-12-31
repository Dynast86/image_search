package com.cgkim.image_search.data

import com.cgkim.image_search.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImageRepository {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.host)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: KakaoService by lazy {
        retrofit.create(KakaoService::class.java)
    }
}