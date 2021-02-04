package com.cgkim.image_search

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImageSearchApplication : Application() {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com"
    }
}