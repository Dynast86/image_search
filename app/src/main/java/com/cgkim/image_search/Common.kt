package com.cgkim.image_search

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Common {

    companion object {
        private const val DATE_TYPE = "yyyy-MM-dd"

        fun getCurrentDate(date: String?) : String? {
            if (date == null) return null

            val dateFormat = SimpleDateFormat(DATE_TYPE, Locale.KOREA)
            return dateFormat.parse(date).toString()
        }
    }
}