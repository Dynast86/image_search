package com.cgkim.image_search

import android.app.Application
import com.cgkim.image_search.data.ApiService
import com.cgkim.image_search.repo.Repository
import com.cgkim.image_search.repo.RepositoryImpl
import com.cgkim.image_search.ui.main.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageSearchApplication : Application() {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com"
    }

    private var myModule = module {
        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single {
            get<Retrofit>().create(ApiService::class.java)
        }
        single<Repository> { RepositoryImpl(get()) }
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