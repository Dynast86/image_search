package com.cgkim.image_search.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgkim.image_search.model.ImageDocument
import com.cgkim.image_search.model.ImageMeta
import com.cgkim.image_search.repo.Repository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()

    private val list = mutableListOf<ImageDocument>()
    private val _itemList = MutableLiveData<List<ImageDocument>>()
    val itemList: LiveData<List<ImageDocument>> = _itemList

    val meta: MutableLiveData<ImageMeta> = MutableLiveData()
    val queryText: MutableLiveData<String> = MutableLiveData()

    init {
        _itemList.value = list
        loading(false)
        isError(false)
        errorMessage(null)
    }

    val searchByKeyWord = fun(query: String, page: Int) {
        clear()
        request(query, page)
    }

    val nextTextValue = fun(newText: String) {
        if (newText.isEmpty()) {
            loading(false)
            isError(false)
            errorMessage(null)
        }
        queryText.postValue(newText)
    }

    fun request(query: String, page: Int) {
        if (isLoading() == true)
            return

        loading(true)
        viewModelScope.launch {
            val item = repository.getItems(query, page)
            if (item.documents.isEmpty()) {
                emptyData()
            } else {
                addItem(item.documents)
            }

            meta.value = item.meta
            loading(false)
        }
    }

    private fun loading(bool: Boolean) {
        isLoading.value = bool
    }

    fun isLoading(): Boolean? {
        return isLoading.value
    }

    private fun errorMessage(error: String?) {
        if (error != null) {
            isError(true)
        }
        errorMessage.value = error
    }

    private fun isError(bool: Boolean) {
        isError.value = bool
    }

    private fun emptyData() {
        loading(false)
        isError(true)
    }

    private fun addItem(item: List<ImageDocument>) {
        list.addAll(item)
        _itemList.postValue(list)
    }

    private fun clear() {
        list.clear()
        _itemList.postValue(list)
    }
}