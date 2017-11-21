package com.dropkick.soma.somaproject.util

import com.dropkick.soma.somaproject.R

/**
 * Created by junhoe on 2017. 11. 16..
 */
class TextUtils {

    companion object {
        fun convertNumToAddZeroText(fromNumber: Int, digitCount: Int): String {
            return if (fromNumber < Math.pow(10.0, digitCount.toDouble() - 1f).toInt()) {
                "0" + fromNumber.toString()
            } else {
                fromNumber.toString()
            }
        }
    }
}