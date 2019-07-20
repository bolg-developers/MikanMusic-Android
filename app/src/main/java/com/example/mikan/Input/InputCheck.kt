package com.example.mikan.Input

/**
 * エラーチェッククラス
 * */
class InputCheck() {
    /**
     * mailAdrresの確認
     * @param str 入力文字列
     * @param cf 比較文字
     * @return 比較文字した文字が何文字めに存在するか
     *
     * */
    fun emailcheck(str: String ,cf:Char): Int =
        first(str) { it == cf }

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
