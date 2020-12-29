package com.cgkim.image_search.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgkim.image_search.data.ImageApi
import com.cgkim.image_search.data.ImageModel
import com.cgkim.image_search.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val editSearchTxt: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val imageItems: MutableLiveData<ImageModel> = MutableLiveData()


    fun request(query: String) {
        loading(true)

        viewModelScope.launch(Dispatchers.IO) {
            println("request")
            val result = try {
                ImageApi().requestQuery(query)
            } catch (e: Exception) {
                Result.Error(Exception("Network request failed"))
            }

            launch(Dispatchers.Main) {
                when (result) {
                    is Result.Success<ImageModel?> -> {
                        val imageModel = result.data
                        setImageItems(imageModel)
                    }
                    else -> {
                        val message = (result as Result.Error).exception.message
                        errorMessage(message.toString())
                    }
                }
                loading(false)
            }
        }
    }

    init {
        loading(false)
        errorMessage(null)
    }

    private fun loading(bool: Boolean) {
        isLoading.value = bool
    }

    private fun errorMessage(error: String?) {
        errorMessage.value = error
    }

    private fun setImageItems(items: ImageModel?) {
        if (items == null)
            return

        imageItems.value = items
    }
}