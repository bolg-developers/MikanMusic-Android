package com.example.mikan.Sign.SignUp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.mikan.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.example.mikan.ItemList
import com.example.mikan.Input.*
import android.text.Editable
import android.widget.Toast.LENGTH_LONG
import com.example.mikan.Input.CustomTextWatcher
import com.example.mikan.DB.DBContract
import com.example.mikan.DB.DBHelper
import com.example.mikan.DB.TaskModel
import com.example.mikan.Interface.CustomTextWatcherListener


class SignUpActivity :AppCompatActivity(),View.OnClickListener, CustomTextWatcherListener {

    val MaxInput = 255                            // email.passwordの入力最大文字数
    val NameInput = 32                            // displaynameの最大文字数
    val MinInput = 8                              // 上記の最小文字数

    val dbhelper = DBHelper(this)        // データベースヘルパー
    val inputcheck = InputCheck()                 //入力エラーチェッククラス

    /**
     * onCreate
     * Activity生成時
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // click設定
        profileimg.setOnClickListener(this)
        login.setOnClickListener(this)

        // 入力初期状態
        if (email.editableText.isEmpty()) {
            email.setError("必須入力")
        }
        if (password.text.isEmpty()) {
            password.setError("必須入力")
        }
        if (displayname.text.isEmpty()) {
            displayname.setError("必須入力")
        }

        // 入力監視
        email.apply { addTextChangedListener(CustomTextWatcher(this, this@SignUpActivity)) }
        password.apply { addTextChangedListener(CustomTextWatcher(this, this@SignUpActivity)) }
        displayname.apply { addTextChangedListener(CustomTextWatcher(this, this@SignUpActivity)) }

        // Spinner項目
        val arr_year =  Array(29, { i -> (i + 1990).toString() })
        val arr_month = Array(12, { i -> (i + 1).toString() })
        val arr_dayys = Array(31, { i -> (i + 1).toString() })

        // Spinner登録
        SpinnerInit(arr_year, "yaer")
        SpinnerInit(arr_month, "month")
        SpinnerInit(arr_dayys, "day")
    }


    /**
     * SpinnerInit(itemArray: Array<String>, spinnerName: String)
     * @param itemArray    アイテムリスト
     * @param spinnerName  spinnerのId名
     * Spinnerの初期登録関数
     * */
    fun SpinnerInit(itemArray: Array<String>, spinnerName: String) {

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //SpinnerのViewIDを抽出
        val viewId = resources.getIdentifier(spinnerName, "id", packageName)
        val spinner = findViewById<Spinner>(viewId)

        //アダプターを設定
        spinner.adapter = adapter

        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {

               // val spinner = parent as Spinner
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    /**
     * LoginEmptyCheck
     * @param numMmap  必須条件の内容
     * 入力情報の空白チェック
     **/
    fun LoginEmptyCheck(numMmap: MutableMap<Int, String>):Boolean
    {
        if(numMmap[1].toString().isEmpty()){
            Toast.makeText(applicationContext, "enailが入力されていません", Toast.LENGTH_LONG).show()
            //email.setError("必須です！入力してください")
        }else if(numMmap[2].toString().isEmpty()){
            Toast.makeText(applicationContext, "paasswordが入力されていません", Toast.LENGTH_LONG).show()
            //password.setError("必須です！入力してください")
        }else if(numMmap[3].toString().isEmpty()){
            Toast.makeText(applicationContext, "displaynameが入力されていません", Toast.LENGTH_LONG).show()
            //displayname.setError("必須です！入力してください")
        }else{
            return false
        }
        return true
    }

    /**
     * onClick
     * クリック処理
     * @param view  ビュー
     * login        ログインボタン
     * profileimg   イメージ画像変更ボタン
     * */
    override fun onClick(view: View?) {

        // 必須条件
        val numMmap: MutableMap<Int, String> =
            mutableMapOf(1 to email.text.toString(), 2 to password.text.toString(), 3 to displayname.text.toString())

        when (view?.id) {

            R.id.login -> {
                // 空白チェック
                if (!LoginEmptyCheck(numMmap) ) {

                    // 取得したデータをデータベースに入れる
                    val task =
                        TaskModel(DBContract.TaskEntry.TASK_NAME, "0", numMmap[3].toString())

                    val result = dbhelper.insertTask(task)

                    if (result) {
                        Toast.makeText(this, "DBInputDone", LENGTH_LONG).show()
                    }

                    // メインとなるActivityに遷移
                    val intent = Intent(this, ItemList::class.java)
                    startActivity(intent)
                }
            }
            R.id.profileimg -> {
                Log.d("btn", "画像変更")
                profileimg.setImageResource(R.mipmap.tubasa)
            }
        }
    }

    /**
     * afterTextChanged
     * @param view
     * @param s
     * EditTextの内容の監視
     * 入力条件を満たしていないときのアクション
     * */
    override fun afterTextChanged(view: View, s: Editable?) {

        when(view){
            email -> { // 条件：最小8～最大255
                if(s.toString().isEmpty()){
                    email.setError("入力必須です")
                }
                val e =  inputcheck.emailcheck(s.toString(),'@')

                if(e == -1){
                    email.setError("@をつけてください")
                }

                if(s.toString().length > 255){
                    email.setError("上限です")
                    Log.d("hs/input","emailは" + s.toString().length.toString())
                }
            }
            password -> { // 条件：最小8～最大255
                if(s.toString().isEmpty()){
                    password.setError("入力必須です")
                }

                if(s.toString().length < MinInput){
                    password.setError("$MinInput 以上入力してください")
                    Log.d("hs/input","passwordは" + s.toString().length.toString())
                }
            }
            displayname -> { // 条件：最大32文字

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

                if(s.toString().isEmpty()){
                    displayname.setError("入力必須です")
                }

                if(s.toString().length > NameInput){
                    displayname.setError("$NameInput 文字が上限です")
                }
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
     *
     * */
    override fun beforeTextChanged(view: View, s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    /**
     * onTextChanged
     * @param view
     * @param str
     * @param start
     * @param  before
     * @param count
     *
     * */
    override fun onTextChanged(view: View, s: CharSequence?, start: Int, before: Int, count: Int) {

    }


    /**
     * onStart()
     * アクティビティが呼ばれたとき
     */
    override fun onStart() {
        super.onStart()
        // サーバからユーザー情報を取得しDBに格納
        // サーバから画像イメージを取得

    }

    /**
     * onRestart()
     * Activityが返ってきたとき
     * */
    override fun onRestart() {
        super.onRestart()

        // edittextのリセット
        if(!email.editableText.isEmpty()){
            email.editableText.clear()
        }
        if(!password.editableText.isEmpty()){
            password.editableText.clear()
        }
        if(!displayname.editableText.isEmpty()){
            displayname.editableText.clear()
        }
    }
}