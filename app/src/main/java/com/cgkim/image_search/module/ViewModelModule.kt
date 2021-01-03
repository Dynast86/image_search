package com.cgkim.image_search.module

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.LARGE
import com.bumptech.glide.Glide

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
}