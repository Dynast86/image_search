package com.cgkim.image_search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDocument(
    var collection: String,
    var thumbnail_url: String,
    var image_url: String,
    var width: Int,
    var height: Int,

    var display_sitename: String,
    var doc_url: String,
    var datetime: String,
) : Parcelable