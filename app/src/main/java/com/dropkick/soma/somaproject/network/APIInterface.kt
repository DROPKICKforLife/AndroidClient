package com.dropkick.soma.somaproject.network

import com.dropkick.soma.somaproject.network.data.UploadPhotoModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by junhoe on 2017. 11. 24..
 */
interface APIInterface {

    @Multipart
    @POST("recv/")
    fun uploadPhoto(@Part("userid") userId: RequestBody,
                    @Part("identify") identify: RequestBody,
                    @Part image: MultipartBody.Part) : Observable<UploadPhotoModel.Response>
}