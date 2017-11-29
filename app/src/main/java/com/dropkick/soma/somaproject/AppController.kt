package com.dropkick.soma.somaproject

import android.app.Application
import com.dropkick.soma.somaproject.network.APIInterface
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by junhoe on 2017. 11. 24..
 */
class AppController : Application() {

    companion object {
        const val BASE_URL = "http://ec2-52-78-149-26.ap-northeast-2.compute.amazonaws.com:8001/"
        fun retrofitCreate(): APIInterface {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(APIInterface::class.java)
        }
    }
}