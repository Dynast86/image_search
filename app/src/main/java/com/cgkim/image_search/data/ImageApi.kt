package com.cgkim.image_search.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class ImageApi {

    companion object {
        const val EMPTY_ITEM = "empty_item"
    }

    @ExperimentalCoroutinesApi
    fun fetch(query: String, page: Int) = callbackFlow<ImageModel> {
        ImageRepository.service.getImageList(query, page).enqueue(object : Callback<ImageModel> {
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