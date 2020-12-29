package com.cgkim.image_search.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide


class ImageViewModel : ViewModel() {

    var displaySiteName: MutableLiveData<String> = MutableLiveData()
    var dateTime: MutableLiveData<String> = MutableLiveData()

    var imgUrl: MutableLiveData<String> = MutableLiveData()
}