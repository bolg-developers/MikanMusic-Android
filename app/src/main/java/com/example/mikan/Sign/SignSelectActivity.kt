package com.example.mikan.Sign

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mikan.R
import com.example.mikan.Sign.SignIn.SignInActivity
import com.example.mikan.Sign.SignUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_signselect.*

class SignSelectActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signselect)

        // サインアップ遷移
        s_up.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // サインイン遷移
        s_in.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

}