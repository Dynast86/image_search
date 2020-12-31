package com.cgkim.image_search.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgkim.image_search.R
import com.cgkim.image_search.data.ImageRepository
import com.cgkim.image_search.databinding.ActivityMainBinding
import com.cgkim.image_search.ui.adapter.CustomRecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    companion object {
        const val DELAY: Long = 1000
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEditText: EditText
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            model = searchViewModel
        }

        initView()
        initObserve()
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.recyclerView)
        mEditText = findViewById(R.id.search_edit)
        mRecyclerView.apply {
            adapter = CustomRecyclerView(this@MainActivity, null)
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            setHasFixedSize(true)
        }
        mRecyclerView.addOnScrollListener(onScrollListener)
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager

            if (hasNextPage()) {
                val lastVisibleItem =
                    (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

                if (layoutManager.itemCount <= lastVisibleItem + 3) {
                    page++
                    val model = searchViewModel
                    model.request(mEditText.editableText.toString(), page)
                }
            }
        }
    }

    private fun hasNextPage(): Boolean {
        val model = searchViewModel.imageItems.value
        return model?.meta?.is_end != null
    }

    private val itemObserver = Observer<ImageRepository> { item ->
        val repo: CustomRecyclerView = mRecyclerView.adapter as CustomRecyclerView
        if (page == 1) repo.resetItems()

        repo.addItems(item.documents)
    }

    private fun initObserve() {
        searchViewModel.imageItems.observe(this, itemObserver)
        searchViewModel.editSearchTxt.observe(this, {
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    page = 1
                    if (it == mEditText.editableText.toString()) {
                        searchViewModel.request(mEditText.editableText.toString(), page)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, DELAY)
        })
    }
}