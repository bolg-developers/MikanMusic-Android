package com.example.mikan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mikan.Sign.SignSelectActivity

class MainActivity : AppCompatActivity() {
    //テストのためのタイマー機能
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        /**
         * Handler
         * 2秒後に画面遷移
         * 戻るボタン押下時に対応のためonResumeに配置
         * */

        mHandler.postDelayed(Runnable {
            val intent = Intent(this, SignSelectActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}
