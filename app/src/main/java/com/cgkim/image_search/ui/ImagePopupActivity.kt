package com.cgkim.image_search.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cgkim.image_search.R
import com.cgkim.image_search.data.ImageDocument
import com.cgkim.image_search.databinding.ActivityImagePopupBinding

class ImagePopupActivity : AppCompatActivity(), View.OnClickListener {

    private val imageViewModel = ImageViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityImagePopupBinding>(this, R.layout.activity_image_popup).apply {
            lifecycleOwner = this@ImagePopupActivity
            model = imageViewModel
        }

        initData()
    }

    private fun initData() {
        val document: ImageDocument? = intent.extras?.get("data") as ImageDocument?

        imageViewModel.displaySiteName.apply {
            if (!TextUtils.isEmpty(document?.display_sitename)) {
                value = getString(R.string.display_site_name, document?.display_sitename)
            }
        }
        imageViewModel.dateTime.apply {
            if (!TextUtils.isEmpty(document?.datetime)) {
                value = getString(R.string.date_time, document?.datetime)
            }
        }
        imageViewModel.imgUrl.value = document?.image_url
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        if (id == R.id.imageButton) {
            finish()
        }
    }
}