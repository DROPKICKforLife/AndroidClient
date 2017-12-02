package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 1..
 */
object Login {

    data class Response(val result: String)

    data class Request(val usertoken: String)
}