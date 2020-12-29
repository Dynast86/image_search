package com.cgkim.image_search.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cgkim.image_search.R
import com.cgkim.image_search.data.ImageItem
import com.cgkim.image_search.databinding.ActivityImagePopupBinding

class ImagePopupActivity : AppCompatActivity(), View.OnClickListener {

    private val imageViewModel = ImageViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityImagePopupBinding>(
            this,
            R.layout.activity_image_popup
        ).apply {
            lifecycleOwner = this@ImagePopupActivity
            imageModel = imageViewModel
        }

        initData()
    }

    private fun initData() {
        val item: ImageItem? = intent.extras?.get("data") as ImageItem?

        imageViewModel.displaySiteName.apply {
            if (!TextUtils.isEmpty(item?.displaySiteName)) {
                value = getString(R.string.display_site_name, item?.displaySiteName)
            }
        }
        imageViewModel.dateTime.apply {
            if (!TextUtils.isEmpty(item?.dateTime)) {
                value = getString(R.string.date_time, item?.dateTime)
            }
        }
        imageViewModel.imgUrl.value = item?.imageUrl
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        if (id == R.id.imageButton) {
            finish()
        }
    }
}