package com.cgkim.image_search.extension

import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.cgkim.image_search.R
import com.cgkim.image_search.model.ImageDocument
import com.cgkim.image_search.ui.adapter.SearchListAdapter


// Loading Image(Glide)
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    val context = imageView.context
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.setStyle(CircularProgressDrawable.LARGE)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide
        .with(context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_baseline_error_24)
        .into(imageView)
}

// SearchView Listener
@BindingAdapter(value = ["onQueryTextListener", "setQueryText"], requireAll = false)
fun setOnQueryTextListener(view: SearchView, searchByKeyWord: (String, Int) -> Unit, setQueryText: (String) -> Unit) {
    view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let {
                setQueryText(it)
            }
            return false
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { keyWord ->
                searchByKeyWord(keyWord, 1)
            }
            view.clearFocus()
            return false
        }
    })
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, baseAdapter: RecyclerView.Adapter<*>) {
    view.apply {
        adapter = baseAdapter
        layoutManager = GridLayoutManager(view.context, 3)
        setHasFixedSize(true)
    }
}

@BindingAdapter("items")
fun setItems(view: RecyclerView, items: MutableList<ImageDocument>?) {
    val searchAdapter = view.adapter as SearchListAdapter

    if (searchAdapter.itemList.size == 0) {
        searchAdapter.itemList = items ?: mutableListOf()
    } else {
        items?.let { searchAdapter.itemList = it }
        searchAdapter.notifyDataSetChanged()
    }
}