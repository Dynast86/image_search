package com.cgkim.image_search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class QueryRepository(meta: JSONObject?, document: JSONArray?) {

    val mMeta: JSONObject? = meta
    val mDocument: JSONArray? = document

    suspend fun makeQuery(text: String): Result<ImageItem>? {
//        return withContext(Dispatchers.IO) {
//
//        }
        return null
    }

    companion object {
        fun parse(input: InputStream): QueryRepository {

            val response = StringBuffer()

            BufferedReader(InputStreamReader(input)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
            }

            var document: JSONArray? = null
            var meta: JSONObject? = null
            try {
                println("response : $response")
                val res = JSONObject(response.toString())
                document = res.getJSONArray("documents")
                meta = res.getJSONObject("meta")
            } catch (e: Exception) {
                e.printStackTrace()
                document = null
                meta = null
            } finally {
                return QueryRepository(meta, document)
            }
        }
    }
}