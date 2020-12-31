package com.cgkim.image_search.data

import com.cgkim.image_search.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class ImageApi {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.host)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @ExperimentalCoroutinesApi
    fun fetch(query: String, page: Int) = callbackFlow<ImageRepository> {
        val service = retrofit.create(KakaoService::class.java)
        service.getImageList(query, page).enqueue(object : Callback<ImageRepository> {
            override fun onResponse(
                call: Call<ImageRepository>,
                response: Response<ImageRepository>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { offer(it) } ?: cancel("empty_item")
                } else {
                    cancel("${response.code()} + ${response.errorBody()}")
                }
                close()
            }

            override fun onFailure(call: Call<ImageRepository>, t: Throwable) {
                cancel(CancellationException(t.message, t))
                close()
            }
        })
        awaitClose()
    }.flowOn(Dispatchers.IO)
}