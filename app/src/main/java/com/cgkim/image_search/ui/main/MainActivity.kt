package com.cgkim.image_search.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgkim.image_search.R
import com.cgkim.image_search.databinding.ActivityMainBinding
import com.cgkim.image_search.ui.adapter.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val listAdapter = SearchListAdapter()

    private lateinit var mRecyclerView: RecyclerView

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            adapter = listAdapter
            model = searchViewModel
        }

        initView()
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.addOnScrollListener(onScrollListener)
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager

            if (hasNextPage()) {
                val lastVisibleItem =
                    (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

                val model = searchViewModel
                if (layoutManager.itemCount <= lastVisibleItem + 3 && model.isLoading() == false) {
                    page++
                    model.request(model.queryText.value!!, page)
                }
            }
        }
    }

    private fun hasNextPage(): Boolean {
        val model = searchViewModel.meta.value
        return model?.is_end != null
    }
}