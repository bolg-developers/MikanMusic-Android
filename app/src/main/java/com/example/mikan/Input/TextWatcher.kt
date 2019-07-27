package com.example.mikan.Input

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.mikan.Interface.CustomTextWatcherListener

/**
 * CustomTextWatcher
 * @param view: ビュー
 * @param listener :CustomTextWatcherListener
 * @return TextWatcher :エディットテキストの状態を監視する
 * */
class CustomTextWatcher (val view: View, val listener: CustomTextWatcherListener) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        listener.afterTextChanged(view, s) //上で作成したリスナーに処理を渡す
    }

    override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
        listener.beforeTextChanged(view, s, start, count, after)
    }

    override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int) {
        listener.onTextChanged(view, s, start, before, count)
    }

}