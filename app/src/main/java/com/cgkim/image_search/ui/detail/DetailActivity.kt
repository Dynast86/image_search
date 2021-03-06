package com.cgkim.image_search.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cgkim.image_search.R
import com.cgkim.image_search.databinding.ActivityImagePopupBinding
import com.cgkim.image_search.model.ImageDocument

class DetailActivity : AppCompatActivity() {

    private val detailViewModel = DetailViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityImagePopupBinding>(this, R.layout.activity_image_popup).apply {
            lifecycleOwner = this@DetailActivity
            model = detailViewModel
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {
        val document: ImageDocument? = intent.extras?.get("DATA") as ImageDocument?

        document?.displaySiteName?.let {
            detailViewModel.setDisplaySiteName(getString(R.string.display_site_name, it))
        }
        document?.datetime?.let {
            detailViewModel.setDateTime(getString(R.string.date_time, it))
        }
        document?.imageUrl?.let {
            detailViewModel.setImageUrl(it)
        }
    }
}