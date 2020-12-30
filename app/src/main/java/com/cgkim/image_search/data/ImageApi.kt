package com.cgkim.image_search.data

import com.cgkim.image_search.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ImageApi {

    fun requestQuery(query: String?, page: Int): Result<ImageModel?> {
        val url =
            URL(BuildConfig.host + BuildConfig.url + "?query=" + query + "&size=30" + "&page=" + page)

        val basicAuth = "KakaoAK " + BuildConfig.kakaoAK

        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", basicAuth)
            doOutput = true

            return if (responseCode == 200) {
                Result.Success(parse(inputStream))
            } else
                Result.Error(Exception(responseMessage))
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }

    fun fetch(query: String?, page: Int): Flow<ImageModel?> = flow {
        val url =
            URL(BuildConfig.host + BuildConfig.url + "?query=" + query + "&size=30" + "&page=" + page)

        val basicAuth = "KakaoAK " + BuildConfig.kakaoAK

        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", basicAuth)
            doOutput = true

            if (responseCode == 200) {
                emit(parse(inputStream))
//                Result.Success(parse(inputStream))
            } else {
                error(Exception(responseMessage))
            }
//                Result.Error(Exception(responseMessage))
        }
        error(Exception("Cannot open HttpURLConnection"))
//        Result.Error(Exception("Cannot open HttpURLConnection"))

    }

    private fun parse(input: InputStream): ImageModel? {
        val response = StringBuffer()
        BufferedReader(InputStreamReader(input)).use {
            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            it.close()
        }

        val document: JSONArray?
        val meta: JSONObject?
        try {
            val res = JSONObject(response.toString())
            document = res.getJSONArray("documents")
            meta = res.getJSONObject("meta")

            var models: ArrayList<ImageItem>? = null

            if (document != null) {
                models = ArrayList()
                for (idx in 0 until document.length()) {
                    val item = document.getJSONObject(idx)

                    models.add(
                        ImageItem(
                            idx.toLong(),
                            item.getString("collection"),
                            item.getString("thumbnail_url"),
                            item.getString("image_url"),
                            item.getInt("width"),
                            item.getInt("height"),
                            item.getString("display_sitename"),
                            item.getString("doc_url"),
                            item.getString("datetime")
                        )
                    )
                }
            }

            return ImageModel(
                meta?.getInt("total_count"),
                meta?.getInt("pageable_count"),
                meta?.getBoolean("is_end"),
                models
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}