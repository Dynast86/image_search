package com.cgkim.image_search.ui

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgkim.image_search.data.ImageApi
import com.cgkim.image_search.data.ImageItem
import com.cgkim.image_search.data.ImageModel
import com.cgkim.image_search.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {

    val editSearchTxt: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()
    val imageItems: MutableLiveData<ImageModel> = MutableLiveData()

    fun request(query: String, page: Int) {
        isError(false)
        loading(true)

        if (TextUtils.isEmpty(query)) {
            emptyData()
            return
        }

        viewModelScope.launch {
            ImageApi().fetch(query, page)
                .catch { cause ->
                    val message = cause.message
                    errorMessage(message.toString())
                }.onCompletion {
                    loading(false)
                }.collect {
                    if (it.totalCount == 0) {
                        emptyData()
                    } else {
                        isError(false)
                        setImageItems(it)
                    }
                }
        }
    }

    init {
        loading(false)
        isError(false)
        errorMessage(null)
    }

    private fun resetItems() {
        imageItems.value = ImageModel(null, null, null, null)
    }

    private fun loading(bool: Boolean) {
        isLoading.value = bool
    }

    private fun errorMessage(error: String?) {
        if (error != null) {
            isError(true)
        }
        errorMessage.value = error
    }

    private fun setImageItems(items: ImageModel?) {
        if (items == null)
            return

        imageItems.value = items
    }

    private fun isError(bool: Boolean) {
        isError.value = bool
    }

    private fun emptyData() {
        loading(false)
        isError(true)
        resetItems()
    }
}