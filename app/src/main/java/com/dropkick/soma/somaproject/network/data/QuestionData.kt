package com.dropkick.soma.somaproject.network.data

/**
 * Created by junhoe on 2017. 12. 10..
 */
object QuestionData {

    data class QuestionList(val result: List<String>)

    data class QuestionSendData(val child_id: String, val questions: List<String>, val contents: List<String>, val key: String)

    data class SendResult(val result: String)
}