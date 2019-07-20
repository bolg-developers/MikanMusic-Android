package com.example.mikan.Interface

import android.text.Editable
import android.view.View


interface CustomTextWatcherListener {
    /**
     * afterTextChanged
     * 最後にこのメソッドが呼び出される
     * @param view :ビュー
     * @param s :最終的にできた修正可能な、変更された文字列
     */
    fun afterTextChanged(view: View, s: Editable?)

    /**
     * beforeTextChanged
     * 文字列が修正される直前に呼び出されるメソッド
     * @param view :ビュー
     * @param s     : 現在EditTextに入力されている文字列
     * @param start : sの文字列で新たに追加される文字列のスタート位置
     * @param count : sの文字列の中で変更された文字列の総数
     * @param after : 新規に追加された文字列の数
     */
    fun beforeTextChanged(view: View, s: CharSequence?, start: Int, count: Int, after: Int)

    /**
     * onTextChanged
     * 文字１つを入力した時に呼び出される
     * @param view :ビュー
     * @param s     : 現在EditTextに入力されている文字列
     * @param start : sの文字列で新たに追加される文字列のスタート位置
     * @param count : 削除される既存文字列の数
     * @param after : 新たに追加された文字列の数
     */
    fun onTextChanged(view: View, s: CharSequence?, start: Int, before: Int, count: Int)

}