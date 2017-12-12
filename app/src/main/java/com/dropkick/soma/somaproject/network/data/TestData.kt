package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 9..
 */
object TestData {

    data class Response(val result: Result)

    data class Result(val title: String, val content: String)

}