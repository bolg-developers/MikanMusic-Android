package com.example.mikan.Sign.SignUp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.mikan.R
import kotlinx.android.synthetic.main.activity_signup.*
import com.example.mikan.ProfileActivity
import com.example.mikan.Input.*
import android.text.Editable
import com.example.mikan.CustomTextWatcher
import com.example.mikan.Interface.CustomTextWatcherListener





class SignUpActivity :AppCompatActivity(),View.OnClickListener, CustomTextWatcherListener {

    val MaxInput = 255                // 最大文字数
    val NameInput = 32                // displaynameの最大文字数
    val MinInput = 8                  // 最小文字数

    val inputcheck = InputCheck()

    /**
     * onCreate処理
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        profileimg.setOnClickListener(this)
        login.setOnClickListener(this)

        // 入力初期状態
        if(email.text.isEmpty()){email.setError("必須入力")}
        if(password.text.isEmpty()){password.setError("必須入力")}
        if(displayname.text.isEmpty()){displayname.setError("必須入力")}

        // 入力監視
        email.apply { addTextChangedListener(CustomTextWatcher(this, this@SignUpActivity)) }
        password.apply { addTextChangedListener(CustomTextWatcher(this, this@SignUpActivity)) }
        displayname.apply { addTextChangedListener(CustomTextWatcher(this, this@SignUpActivity)) }

        // スピナー登録
        val yadapter = ArrayAdapter.createFromResource(this,
            R.array.yearspinner_values,
            R.layout.support_simple_spinner_dropdown_item
        )
        val madapter = ArrayAdapter.createFromResource(this,
            R.array.monthspinners_values,
            R.layout.support_simple_spinner_dropdown_item
        )
        val dadapter = ArrayAdapter.createFromResource(this,
            R.array.dayspinners_values,
            R.layout.support_simple_spinner_dropdown_item
        )
        // ドロップダウン形式
        yadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // adapterセット
        yaer.adapter = yadapter
        month.adapter = madapter
        day.adapter = dadapter

        /**
         * 生年月日入力設定
         */
        // リスナーを登録
        yaer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                // Kotlin Android Extensions
                yaertext.text = item
            }
            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // リスナーを登録
        month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                // Kotlin Android Extensions
                monthtext.text = item
            }
            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        // リスナーを登録
        day.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                // Kotlin Android Extensions
                daytext.text = item
            }
            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
}
    /**
     * onClick
     * クリック処理
     * @param v
     * login      : ログインボタン
     * profileimg : イメージ画像変更ボタン
     * */
    override fun onClick(v: View?) {
        // 必須条件
        val numMmap: MutableMap<Int, String> =
            mutableMapOf(1 to email.text.toString(), 2 to password.text.toString(), 3 to displayname.text.toString())

        when (v?.id) {     //switch文はないのでwhen文を使う
            R.id.login -> {

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
                    // 取得したデータをデータベースに入れる


                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.profileimg -> {
                Log.d("btn", "画像変更")
                profileimg.setImageResource(R.mipmap.tubasa)
            }
        }

    }

    // EditTextの内容の監視
    // 入力条件を満たしていないときのアクション

    override fun afterTextChanged(view: View, s: Editable?) {
        // 初期状態

        when(view){
            email -> {
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
                // 空白でないか
                //DBにアクセスし同じ文字になっていないか/既に登録されていないか
            }
            password -> { // 最小8～最大255
                if(s.toString().isEmpty()){
                    password.setError("入力必須です")
                }

                if(s.toString().length < MinInput){
                    password.setError("$MinInput 以上入力してください")
                    Log.d("hs/input","passwordは" +s.toString().length.toString())
                }

            }
            displayname -> { //最大32文字
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

    override fun beforeTextChanged(view: View, s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(view: View, s: CharSequence?, start: Int, before: Int, count: Int) {

    }


    /***
     * アクティビティが呼ばれたとき
     */
    override fun onStart() {
        super.onStart()
        // サーバからユーザー情報を取得しDBに格納
        // サーバから画像イメージを取得

    }
}