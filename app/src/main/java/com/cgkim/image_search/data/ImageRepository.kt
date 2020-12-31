package com.cgkim.image_search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageRepository(
    var meta: ImageMeta?,
    var documents: ArrayList<ImageDocument>?
) : Parcelable

@Parcelize
data class ImageMeta(
    var total_count: Int?,
    var pageable_count: Int?,
    var is_end: Boolean?,
) : Parcelable

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