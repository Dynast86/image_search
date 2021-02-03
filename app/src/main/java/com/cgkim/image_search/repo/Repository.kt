package com.cgkim.image_search.repo

import com.cgkim.image_search.model.ImageModel

interface Repository {
    suspend fun getItems(query: String, page: Int): ImageModel
}