package com.cgkim.image_search.extension

import android.content.Intent
import android.view.View
import com.cgkim.image_search.model.ImageDocument
import com.cgkim.image_search.ui.detail.DetailActivity

class ActionHandler {
    fun onDetailClick(view: View, task: ImageDocument) {
        val context = view.context
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("DATA", task)
        }

        context.startActivity(intent)
    }
}