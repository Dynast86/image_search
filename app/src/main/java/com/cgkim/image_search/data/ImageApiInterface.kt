package com.cgkim.image_search.data

import kotlinx.coroutines.flow.Flow

interface ImageApiInterface {
    fun fetch(query: String, page: Int): Flow<ImageModel>
}