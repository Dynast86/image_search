package com.cgkim.image_search.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.AbsListView
import android.widget.EditText
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cgkim.image_search.R
import com.cgkim.image_search.data.ImageModel
import com.cgkim.image_search.databinding.ActivityMainBinding
import com.cgkim.image_search.ui.adapter.GridViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        const val DELAY: Long = 1000
    }

    private var mGridView: GridView? = null
    private lateinit var mEditText: EditText
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private var page = 1
    private var lastItemVisibleFlag = false

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
        mGridView = findViewById(R.id.gridView)
        mEditText = findViewById(R.id.search_edit)
        mGridView?.adapter = GridViewAdapter(this, null)
        mGridView?.setOnScrollListener(onScrollListener)
        mEditText.setOnEditorActionListener { _, _, _ ->
            false
        }
    }

    private val onScrollListener = object : AbsListView.OnScrollListener {
        override fun onScrollStateChanged(p0: AbsListView?, state: Int) {
            if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastItemVisibleFlag
            ) {
                val model = searchViewModel
                val imageModel = model.imageItems.value
                if (imageModel?.isEnd == false) {
                    page++

                    model.request(mEditText.editableText.toString(), page)
                }
            }
        }

        override fun onScroll(view: AbsListView?, firstCnt: Int, visibleCnt: Int, totalCnt: Int) {
            lastItemVisibleFlag = (totalCnt > 0) && (firstCnt + visibleCnt >= totalCnt)
        }
    }

    private val itemObserver = Observer<ImageModel> { item ->
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val repoAdapter: GridViewAdapter = mGridView?.adapter as GridViewAdapter
        if (page == 1) repoAdapter.resetItems()

        repoAdapter.addItems(item.imageItem)
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
//                    println("error : " + e.printStackTrace())
                }
            }, DELAY)
        })
    }
}