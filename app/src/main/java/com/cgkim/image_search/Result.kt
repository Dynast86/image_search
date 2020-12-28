package com.cgkim.image_search

import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class LoginRepository {

    fun makeLoginRequest(jsonBody: String?): Result<QueryRepository> {
        val url = URL(BuildConfig.host + BuildConfig.url + "?query=" + jsonBody + "&size=30")

        val basicAuth = "KakaoAK " + BuildConfig.kakaoAK

        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", basicAuth)
            doOutput = true

            if (responseCode == 200)
                return Result.Success(QueryRepository.parse(inputStream))
            else
                return Result.Error(Exception(responseMessage))
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}

