package com.cgkim.image_search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItem(
    var idx: Long,
    var collection: String,
    var thumbnailUrl: String,
    var imageUrl: String,
    var width: Int,
    var height: Int,

    var displaySiteName: String,
    var docUrl: String,
    var dateTime: String,
) : Parcelable