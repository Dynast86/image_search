package com.cgkim.image_search.data

import androidx.annotation.Nullable
import com.cgkim.image_search.BuildConfig
import com.cgkim.image_search.model.ImageModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/search/image")
    @Headers("Authorization: KakaoAK " + BuildConfig.kakaoAK)
    suspend fun getImageList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Nullable
        @Query("size") size: Int = 30
    ): ImageModel
}