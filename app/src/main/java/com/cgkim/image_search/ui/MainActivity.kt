package com.cgkim.image_search.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
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
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        const val DELAY: Long = 1000
    }

    private var mGridView: GridView? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

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
        mGridView?.adapter = GridViewAdapter(this, null)
    }

    private val itemObserver = Observer<ImageModel> { item ->
        val repoAdapter: GridViewAdapter = mGridView?.adapter as GridViewAdapter

        if (item.imageItem != null) {
            repoAdapter.resetItems()
            repoAdapter.addItems(item.imageItem)
        }
    }

    private fun initObserve() {
        searchViewModel.imageItems.observe(this, itemObserver)
        searchViewModel.editSearchTxt.observe(this, {
            if (TextUtils.isEmpty(it)) return@observe

            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    val editText = findViewById<EditText>(R.id.search_edit)
                    if (editText.editableText.toString() != "" && it == editText.editableText.toString()) {
                        searchViewModel.request(it)
                    }
                } catch (e: Exception) {
                    println("error : " + e.printStackTrace())
                }
            }, DELAY)
        })
    }
}