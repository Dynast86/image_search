package com.cgkim.image_search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageRepository(
    var meta: ImageMeta?,
    var documents: ArrayList<ImageDocument>?
) : Parcelable