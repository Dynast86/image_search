package com.cgkim.image_search.data

import com.cgkim.image_search.BuildConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ImageApi {

    fun requestQuery(query: String?, page: Int): Result<ImageRepository> {
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


    suspend fun fetch(query: String?, page: Int): Flow<ImageRepository> = flow {
        val url =
            URL(BuildConfig.host + BuildConfig.url + "?query=" + query + "&size=30" + "&page=" + page)

        val basicAuth = "KakaoAK " + BuildConfig.kakaoAK
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            urlConnection.requestMethod = "GET"
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8")
            urlConnection.setRequestProperty("Accept", "application/json")
            urlConnection.setRequestProperty("Authorization", basicAuth)
            urlConnection.doOutput = true

            if (urlConnection.responseCode == 200) {
                emit(parse(urlConnection.inputStream))
            } else {
                throw Exception(urlConnection.responseMessage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Cannot open HttpURLConnection")
        } finally {
            urlConnection.disconnect()
        }
    }.flowOn(Dispatchers.IO)

    private fun parse(input: InputStream): ImageRepository {
        val response = StringBuffer()
        BufferedReader(InputStreamReader(input)).use {
            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            it.close()
        }

        return Gson().fromJson(response.toString(), ImageRepository::class.java)
    }
}