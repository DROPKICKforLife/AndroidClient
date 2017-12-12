package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 11. 24..
 */
object UploadPhotoModel {

    data class Response(val result: Result)

    data class Result(val imgurl: String, val message: String, val identify: String)
}