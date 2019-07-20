package com.example.mikan.await

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpUtil {
    fun genres(url: String): String? {

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        // レスポンス取得
        val response = client.newCall(request).execute()
        try {
           return response.body()?.string()
        }catch (e :Exception){
            Log.e(javaClass.simpleName, "Failed to get message", e)
            return e.toString()
        }
    }
}