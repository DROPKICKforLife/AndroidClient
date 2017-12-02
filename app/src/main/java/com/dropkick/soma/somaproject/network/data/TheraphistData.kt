package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 3..
 */
object TheraphistData {

    data class Response(val result: List<DetailData>)

    data class DetailData(val profileimgurl: String, val doctorname: String, val speclist: List<String>,
                          val doctorcount: Int, val hospitalname: String, val schoolname: String)
}