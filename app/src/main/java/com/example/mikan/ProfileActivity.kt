package com.example.mikan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.mikan.DB.DBContract
import com.example.mikan.DB.DBHelper
import com.example.mikan.await.HttpUtil
import kotlinx.android.synthetic.main.activity_profile.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class   ProfileActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    val urlReq ="http://ec2-3-112-22-157.ap-northeast-1.compute.amazonaws.com:8080/"
    var text1:TextView? = null
    val dbhelper = DBHelper(this)
    var data:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        text1 = findViewById(R.id.datatext)

        // progressbar設定
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        var cur = dbhelper.GetAll()
            if (cur.moveToFirst()) {
            do {
                data += "DisplayNameは"
                data += "\t"
                data += cur.getString(2)
                data += "です\n"
                Log.d("hs/db","データの内容$data")
            } while (cur.moveToNext())
        }
        cur.close()
        datatext.setText(data)
        dbhelper.closeDB()

        // 情報のpost
        val post = findViewById<Button>(R.id.buttonPost)
        post.setOnClickListener(){

        }

        //情報の取得
        val get = findViewById<Button>(R.id.buttonGet)
        get.setOnClickListener() {
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
