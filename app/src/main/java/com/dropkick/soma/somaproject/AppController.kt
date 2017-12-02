package com.dropkick.soma.somaproject

import android.app.Application
import android.content.Context
import com.dropkick.soma.somaproject.network.APIInterface
import com.kakao.auth.*
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by junhoe on 2017. 11. 24..
 */
class AppController : Application() {

    companion object {
        const val BASE_URL = "http://ec2-52-78-149-26.ap-northeast-2.compute.amazonaws.com/"
        fun retrofitCreate(): APIInterface {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(APIInterface::class.java)
        }
        var instance: AppController? = null

        fun getApplicationContext(): Context? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
    }

    private class KakaoSDKAdapter : KakaoAdapter() {

        override fun getApplicationConfig(): IApplicationConfig {
            return object : IApplicationConfig {
                override fun getApplicationContext(): Context {
                    return AppController.getApplicationContext()!!
                }
            }
        }

        override fun getSessionConfig(): ISessionConfig {
            return object: ISessionConfig {
                override fun isSaveFormData(): Boolean {
                    return true
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_TALK)
                }
            }
        }

    }

}