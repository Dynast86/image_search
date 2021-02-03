package com.cgkim.image_search.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ImageModel(
    @SerializedName("meta")
    var meta: ImageMeta,
    @SerializedName("documents")
    var documents: List<ImageDocument>
)

@Parcelize
data class ImageMeta(
    var total_count: Int?,
    var pageable_count: Int?,
    var is_end: Boolean?,
) : Parcelable

@Parcelize
data class ImageDocument(
    var collection: String?,
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String?,
    @SerializedName("image_url")
    var imageUrl: String?,
    var width: Int?,
    var height: Int?,

    @SerializedName("display_sitename")
    var displaySiteName: String?,
    @SerializedName("doc_url")
    var docUrl: String?,
    var datetime: String?,
) : Parcelable