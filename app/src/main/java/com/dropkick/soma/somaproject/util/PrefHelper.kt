package com.dropkick.soma.somaproject.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * Created by junhoe on 2017. 12. 3..
 */
object PrefHelper {

    const val APP = "APP"
    const val USER_ID = "userToken"
    fun put(key: String, value: String, context: Context) {
        val pref = context.getSharedPreferences(APP, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(context: Context, key: String): String {
        val pref = context.getSharedPreferences(APP, MODE_PRIVATE)
        return pref.getString(key, "")
    }

    fun getId(context: Context): String {
        val pref = context.getSharedPreferences(APP, MODE_PRIVATE)
        return pref.getString(USER_ID, "")
    }
}