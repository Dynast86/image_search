package com.cgkim.image_search

import android.app.Application
import com.cgkim.image_search.data.ImageApi
import com.cgkim.image_search.data.ImageApiInterface
import com.cgkim.image_search.ui.SearchViewModel

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ImageSearchApplication : Application() {

    private var myModule = module {
        single<ImageApiInterface> { ImageApi() }
        viewModel { SearchViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ImageSearchApplication)
            modules(myModule)
        }
    }
}