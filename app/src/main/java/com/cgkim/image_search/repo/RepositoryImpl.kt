package com.cgkim.image_search.repo

import com.cgkim.image_search.data.ApiService
import com.cgkim.image_search.model.ImageModel

class RepositoryImpl(private val service: ApiService) : Repository {
    override suspend fun getItems(query: String, page: Int): ImageModel = service.getImageList(query, page)
}