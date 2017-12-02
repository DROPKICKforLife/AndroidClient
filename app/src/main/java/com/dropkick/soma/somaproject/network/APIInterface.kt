package com.dropkick.soma.somaproject.network

import com.dropkick.soma.somaproject.network.data.TheraphistData
import com.dropkick.soma.somaproject.network.data.Login
import com.dropkick.soma.somaproject.network.data.UploadPhotoModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
}