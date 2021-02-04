package com.cgkim.image_search.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private var _displaySiteName = MutableLiveData<String>()
    val displaySiteName: LiveData<String>
        get() = _displaySiteName

    private var _dateTime = MutableLiveData<String>()
    val dateTime: LiveData<String>
        get() = _dateTime

    private var _imgUrl = MutableLiveData<String>()
    val imgUrl: LiveData<String>
        get() = _imgUrl

    fun setDisplaySiteName(text: String) {
        _displaySiteName.value = text
    }

    fun setDateTime(text: String) {
        _dateTime.value = text
    }

    fun setImageUrl(text: String) {
        _imgUrl.value = text
    }
}