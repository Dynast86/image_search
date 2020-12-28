package com.cgkim.image_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private var mGridView: GridView? = null
    private var mBeforeSearchValue : String? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        mBeforeSearchValue = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView: SearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        println("submit : $query")

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (mBeforeSearchValue == newText)
            return false


        CoroutineScope(Dispatchers.IO).launch {
            mBeforeSearchValue = newText
            when (val result = LoginRepository().makeLoginRequest(newText)) {
                is Result.Success<QueryRepository> -> {
                    val repository = result.data
                    val document = repository.mDocument
                    val meta = repository.mMeta
                    println("document : $document")
                    println("meta : $meta")

                    val gridViewAdapter = mGridView?.adapter as GridViewAdapter

                    val arrayList: ArrayList<ImageItem> = ArrayList()
                    for (idx in 0 until document!!.length()) {
                        val item = document.getJSONObject(idx)

                        arrayList.add(
                            ImageItem(
                                idx.toLong(),
                                item.getString("collection"),
                                item.getString("thumbnail_url"),
                                item.getString("image_url"),
                                item.getInt("width"),
                                item.getInt("height"),
                                item.getString("display_sitename"),
                                item.getString("doc_url"),
                                item.getString("datetime")
                            )
                        )
                    }
                    gridViewAdapter.resetItems()
                    gridViewAdapter.addItems(arrayList)
                }
                else -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.no_response_data),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }

        return true
    }

    private fun init() {
        mGridView = findViewById(R.id.gridView)
        mGridView?.apply {
            adapter = GridViewAdapter(this@MainActivity, null)
        }
    }

    override fun onClose(): Boolean {
        val gridViewAdapter = mGridView?.adapter as GridViewAdapter
        gridViewAdapter.resetItems()

        gridViewAdapter.notifyDataSetChanged()
        return true
    }
}