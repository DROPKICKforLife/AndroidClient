package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 10..
 */
object ResultData {
    data class ResultExistData(val result: String, val week_id: Int)

    data class ResultData(val result: List<String>)
}