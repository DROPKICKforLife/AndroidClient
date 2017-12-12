package com.dropkick.soma.somaproject.network

import com.dropkick.soma.somaproject.network.data.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by junhoe on 2017. 11. 24..
 */
interface APIInterface {

    @Multipart
    @POST("recv/")
    fun uploadPhoto(@Part("userid") userId: RequestBody,
                    @Part("identify") identify: RequestBody,
                    @Part image: MultipartBody.Part) : Observable<UploadPhotoModel.Response>

    @POST("recv/usercheck")
    fun requestLogin(@Body request: Login.Request) : Observable<Login.Response>

    @GET("recv/viewdoctor")
    fun requestDoctorProfile() : Observable<TheraphistData.Response>

    @POST("recv/useradd")
    fun postUserInfo(@Body request: UserData.Request) : Observable<UserData.Response>

    @POST("recv/connect_doctor")
    fun selectDoctor(@Body request: TheraphistData.SelectDoctorData) : Observable<TheraphistData.SelectResponse>

    @GET("recv/getallweeks")
    fun getWeeksProgramInfo() : Observable<ProgramData.Response>

    @GET("recv/getnowweek")
    fun getWeekCount(@Query("childid") userId: String) : Observable<ProgramData.WeekCount>

    @GET("recv/reply_status")
    fun getProgramStatus(@Query("child_id") userId: String) : Observable<ResultData.ResultExistData>

    @GET("recv/doctor_result")
    fun getDoctorResult(@Query("child_id") userId: String, @Query("week_id") weekCount: Int) : Observable<ResultData.ResultData>

    @GET("recv/get_week_content")
    fun getTestList(@Query("week_num") weekCount: Int) : Observable<TestListData.Response>

    @GET("recv/send_to_child_log")
    fun getQuestionList(@Query("child_id") userId: String, @Query("key") key: String) : Observable<QuestionData.QuestionList>

    @POST("recv/send_to_doctor")
    fun sendQuestionResponse(@Body data: QuestionData.QuestionSendData) : Observable<QuestionData.SendResult>

}