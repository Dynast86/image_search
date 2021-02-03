package com.cgkim.image_search.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgkim.image_search.R
import com.cgkim.image_search.databinding.ActivityMainBinding
import com.cgkim.image_search.ui.adapter.SearchListAdapter
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    companion object {
        const val DELAY: Long = 1000
    }

    private val searchViewModel: SearchViewModel by viewModel()
    private val listAdapter = SearchListAdapter()

    private lateinit var mRecyclerView: RecyclerView
//    private lateinit var mEditText: EditText
//
//    private var queryTextChangeJob: Job? = null

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            adapter = listAdapter
            model = searchViewModel
        }

        initView()
        initObserve()
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

    //
//    private val itemObserver = Observer<List<ImageDocument>> {
//        listAdapter.updateList(it)
//    }

    private fun initObserve() {
//        searchViewModel.itemList.observe(this, itemObserver)
//        searchViewModel.editSearchTxt.observe(this, {
//            queryTextChangeJob?.cancel()
//            queryTextChangeJob = lifecycleScope.launch(Dispatchers.Main) {
//                delay(DELAY)
//
//                page = 1
//                if (it == mEditText.editableText.toString()) {
//                    searchViewModel.request(mEditText.editableText.toString(), page)
//                }
//            }
//        })
    }
}