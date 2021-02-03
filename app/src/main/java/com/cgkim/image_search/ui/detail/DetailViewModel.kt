package com.cgkim.image_search.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {

    var displaySiteName: MutableLiveData<String> = MutableLiveData()
    var dateTime: MutableLiveData<String> = MutableLiveData()

    var imgUrl: MutableLiveData<String> = MutableLiveData()
}