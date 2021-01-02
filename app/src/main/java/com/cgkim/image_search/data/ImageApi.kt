package com.cgkim.image_search.data

import com.cgkim.image_search.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class ImageApi : ImageApiInterface {

    companion object {
        const val EMPTY_ITEM = "empty_item"
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.host)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service: KakaoService by lazy {
        retrofit.create(KakaoService::class.java)
    }

    @ExperimentalCoroutinesApi
    override fun fetch(query: String, page: Int): Flow<ImageModel> {
        return callbackFlow<ImageModel> {
            service.getImageList(query, page).enqueue(object : Callback<ImageModel> {
                override fun onResponse(
                    call: Call<ImageModel>,
                    response: Response<ImageModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { offer(it) } ?: cancel(EMPTY_ITEM)
                    } else {
                        cancel("${response.code()} + ${response.errorBody()}")
                    }
                    close()
                }

                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                    cancel(CancellationException(t.message, t))
                    close()
                }
            })
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }
}