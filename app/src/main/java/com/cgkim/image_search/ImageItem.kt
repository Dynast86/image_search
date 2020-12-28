package com.cgkim.image_search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ImageItem(
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
//data class ImageItem() : Parcelable {
//    var collection: String? = null
//    var thumbnailUrl: String? = null
//    var imageUrl: String? = null
//    var width: Int = 0
//    var height: Int = 0
//
//    var displaySiteName: String? = null
//    var docUrl: String? = null
//    var dateTime: String? = null
//
//
//    constructor(parcel: Parcel) : this() {
//        parcel.run {
//            collection = readString()
//            thumbnailUrl = readString()
//            imageUrl = readString()
//            width = readInt()
//            height = readInt()
//            displaySiteName = readString()
//            docUrl = readString()
//            dateTime = readString()
//        }
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.run {
//            writeString(collection)
//            writeString(thumbnailUrl)
//            writeString(imageUrl)
//            writeInt(width)
//            writeInt(height)
//            writeString(displaySiteName)
//            writeString(docUrl)
//            writeString(dateTime)
//        }
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ImageItem> {
//        override fun createFromParcel(parcel: Parcel): ImageItem {
//            return ImageItem(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ImageItem?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
