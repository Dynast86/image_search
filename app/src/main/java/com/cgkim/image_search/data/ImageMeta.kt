package com.cgkim.image_search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageMeta(
    var total_count: Int?,
    var pageable_count: Int?,
    var is_end: Boolean?,
) : Parcelable