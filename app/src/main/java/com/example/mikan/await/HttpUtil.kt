package com.example.mikan.await

import android.util.Log
import okhttp3.*
import okhttp3.FormBody




class HttpUtil {
    fun get(url: String ): String? {

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

    // iconをアップロード
    fun p_icon(url : String ):String?{

        val JSON =  MediaType.parse("Content-Type: application/json")

        val client = OkHttpClient()
        val json = "{\"email\":\"ilovegame@gmail.com\", " +
                "\"password\":\"lovegame\"," +
                "\"displayName\":\"本田翼\"," +
                "\"iconURL\":\"https://s3-ap-northeast-1.amazonaws.com/mikanmusic/01f9e044-a3bd-46b5-a586-3ccfdfd01aad.jpg\"," +
                "\"introduction\":\"ゲームすきです。\"}"

        val body = RequestBody.create(JSON, json)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        // レスポンス取得
        val response = client.newCall(request).execute()

        try {
            Log.d("hs/resstring",response.body()?.string())
            return response.body()?.string()
        }catch (e :Exception){
            Log.e(javaClass.simpleName, "Failed to get message", e)
            return e.toString()
        }
    }
}