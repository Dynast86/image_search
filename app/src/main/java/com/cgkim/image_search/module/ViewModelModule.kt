package com.cgkim.image_search.module

import android.widget.GridView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.LARGE
import com.bumptech.glide.Glide
import com.cgkim.image_search.data.ImageItem

object ViewModelModule {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String) {
        val context = imageView.context
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.setStyle(LARGE)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide
            .with(context)
            .load(url)
            .placeholder(circularProgressDrawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("items")
    fun setBindItem(view: GridView, items: MutableLiveData<ArrayList<ImageItem>>) {
        view.adapter?.run {
//            if (this is GridViewAdapter) {
//                this.addItems(items)
//            }
        }
    }
}