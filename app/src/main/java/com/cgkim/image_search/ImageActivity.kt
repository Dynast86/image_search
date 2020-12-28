package com.cgkim.image_search

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class ImageActivity : AppCompatActivity() {

    private lateinit var mImageView: ImageView
    private var mDisplaySiteName: TextView? = null
    private var mDateTime: TextView? = null
    private var glide: RequestManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        initView()
        initData()
    }

    private fun initView() {
        glide = Glide.with(this)
        mImageView = findViewById(R.id.imageView)
    }

    private fun initData() {
        val item: ImageItem? = intent.extras?.get("data") as ImageItem?

        glide?.load(item?.imageUrl)
            ?.placeholder(R.mipmap.ic_launcher)
            ?.into(mImageView)

        mDisplaySiteName = findViewById<TextView>(R.id.tvDisplaySiteName).apply {
            text = getString(R.string.display_site_name, item?.displaySiteName)
            visibility = if (item?.displaySiteName == null) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        mDateTime = findViewById<TextView>(R.id.tvDateTime).apply {
            text = getString(R.string.date_time, Common.getCurrentDate(item?.dateTime))
            visibility = if (item?.dateTime == null) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}