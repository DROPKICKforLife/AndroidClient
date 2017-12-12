package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 9..
 */
object ProgramData {

    data class Response(val result: List<String>)

    data class WeekCount(val result: Int)
}