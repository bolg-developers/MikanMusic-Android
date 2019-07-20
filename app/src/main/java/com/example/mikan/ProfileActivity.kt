package com.example.mikan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.mikan.await.HttpUtil
import kotlinx.android.synthetic.main.activity_profile.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    val urlReq ="http://ec2-3-112-22-157.ap-northeast-1.compute.amazonaws.com:8080/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // progressbar設定
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        val button = findViewById<Button>(R.id.buttonGet)
        button.setOnClickListener() {
            // 表示
            progressBar.visibility = View.VISIBLE
            // 非同期処理開始
            gfunc()
        }
    }


    // 全件ジャンル取得タスク
    fun gfunc() = GlobalScope.launch(Dispatchers.Main){
        val ag = HttpUtil()

        // ここで非同期
        async (Dispatchers.Default){ ag.genres(urlReq + "v1/genres")}.await().let {
            // jsonを格納
            val result = JSONObject(it)
            val genres = result.getJSONArray("genres")
                // インデックスで情報を指定する
                val randb = genres.getJSONObject(0)
                val id = randb.getString("id")
                val name = randb.getString("name")

            Log.d("hs/genres",id + '\n' + name)
            progressBar.visibility = View.GONE

        }
    }
}
