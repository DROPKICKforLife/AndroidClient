package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 10..
 */
object TestListData {
    data class Response(val result: List<Element>)

    data class Element(val key: String, val url: String, val content: String, val topic: String)
}