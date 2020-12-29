package com.cgkim.image_search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    var totalCount: Int?,
    var pageableCount: Int?,
    var isEnd: Boolean?,
    var imageItem: ArrayList<ImageItem>?
) : Parcelable