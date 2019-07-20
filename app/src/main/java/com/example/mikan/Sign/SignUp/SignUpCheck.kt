package com.example.mikan.Sign.SignUp

/**
 * エラーチェッククラス
 * */
class Exception() {

    /**
     * mailAdrresの確認
     * @param str
     * */
    fun email_check(str: String): Int =
        first(str) { it == '@' }

    /**
     * 文字列を一文字ずつ調べる
     * 再帰関数
     * @param str
     * @param predicate
     * */
    fun first(str: String, predicate: (Char) -> Boolean): Int {
        tailrec fun go(str: String, index: Int): Int =
            when {
                str.isEmpty() -> -1
                predicate(str.first()) -> index
                else -> go(str.drop(1), index + 1)
            }
        return go(str, 0)
    }

    /**
     * 引数の文字列が空白化どうか判定関数
     * @param str
     * */
    fun firstWhitespace(str: String): Int {
        val isWhitespace: (Char) -> Boolean = {
            it.isWhitespace()
        }
        return first(str, isWhitespace)
    }
}
