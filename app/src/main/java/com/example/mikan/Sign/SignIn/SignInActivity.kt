package com.example.mikan.Sign.SignIn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import com.example.mikan.DB.DBContract
import com.example.mikan.DB.DBHelper
import com.example.mikan.Input.CustomTextWatcher
import com.example.mikan.Input.InputCheck
import com.example.mikan.Interface.CustomTextWatcherListener
import com.example.mikan.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignInActivity:AppCompatActivity(), CustomTextWatcherListener {

    val inputcheck = InputCheck()                 // 入力エラーチェッククラス
    val dbhelper = DBHelper(this)        // データベースヘルパー

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // 入力監視
        email.apply { addTextChangedListener(CustomTextWatcher(this, this@SignInActivity)) }
        password.apply { addTextChangedListener(CustomTextWatcher(this, this@SignInActivity)) }
        displayname.apply { addTextChangedListener(CustomTextWatcher(this, this@SignInActivity)) }

        // 警告の初期化
        if(email.text.isEmpty()){ email.setError("入力必須です") }
        if(password.text.isEmpty()){ password.setError("入力必須です") }
        if(displayname.text.isEmpty()){ displayname.setError("入力必須です") }

        login.setOnClickListener{

            // DBに一致情報が無ければ
            // email,passwordのPOSTを行う

        }
    }

    /**
     * afterTextChanged
     * @param view
     * @param s
     * 最後にこのメソッドが呼び出される
     * 入力条件を満たしていないときのアクションを行う
     * */
    override fun afterTextChanged(view: View, s: Editable?) {

        val MaxInput = 255                            // email.passwordの入力最大文字数
        val NameInput = 32                            // displaynameの最大文字数
        val MinInput = 8                              // 上記の最小文字数

        when(view){
            email -> { // 条件：最小8～最大255
                // @の検知
                if(inputcheck.emailcheck(s.toString(),'@') == -1){
                    email.setError("@をつけてください")
                }

                if(s.toString().length > 255){
                    email.setError("上限です")
                }
            }

            password -> { // 条件：最小8～最大255
                if(s.toString().length < MinInput){
                    password.setError("$MinInput 以上入力してください")
                }
            }

            displayname -> { // 条件：最大32文字

                if(s.toString().length > NameInput){
                    displayname.setError("$NameInput 文字が上限です")
                }

                // 同一displaynameが存在するかチェック
                var cur = dbhelper.GetRecordTask(DBContract.TaskEntry.DISPLAYNAME)
                if (cur.moveToFirst()) {
                    do {
                        // 同じdisplaynameが無いか確認
                        if (cur.getString(0) == s.toString()) {
                            Log.d("hs/db_displayname", "ある")
                            displayname.setError("既に存在しています")
                        }
                    } while (cur.moveToNext())
                }
                cur.close()

            }

            introduction ->{ // 最大255文字
                if(s.toString().length > MaxInput){
                    introduction.setError("$MaxInput 文字が上限です")
                }
            }
        }
    }

    /**
     * beforeTextChanged
     * @param view
     * @param str
     * @param start
     * @param count
     * @param after
     * 文字列が修正される直前に呼び出されるメソッド
     * */
    override fun beforeTextChanged(view: View, s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    /**
     * onTextChanged(view: View, s: CharSequence?, start: Int, before: Int, count: Int)
     * @param view
     * @param str
     * @param start
     * @param  before
     * @param count
     * 文字１つを入力した時に呼び出される
     * */
    override fun onTextChanged(view: View, s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}