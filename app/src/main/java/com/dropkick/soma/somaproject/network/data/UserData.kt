package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 3..
 */
object UserData {

    data class Response(val result: String)

    data class Request(val usertoken: String, val childname: String,
                       val childsex: Int, val childability: Int,
                       val childbirth: String, val prevcontent: String,
                       val hopecontent: String)
}