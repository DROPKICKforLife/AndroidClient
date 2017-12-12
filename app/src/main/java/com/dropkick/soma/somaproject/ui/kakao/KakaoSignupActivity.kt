package com.dropkick.soma.somaproject.ui.kakao

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.network.data.Login
import com.dropkick.soma.somaproject.ui.KidInfoActivity
import com.dropkick.soma.somaproject.ui.MainActivity
import com.dropkick.soma.somaproject.ui.SplashActivity
import com.dropkick.soma.somaproject.util.PrefHelper
import com.kakao.auth.ErrorCode
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.response.model.UserProfile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class KakaoSignupActivity : AppCompatActivity() {

    companion object {
        const val TAG = "KakaoSignupActivity"
    }

    val networkService by lazy {
        AppController.retrofitCreate()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_signup)
        UserManagement.requestMe(object : MeResponseCallback() {
            override fun onSuccess(result: UserProfile?) {
                val token = result?.id
                Log.i(TAG, "${result?.id}")
                PrefHelper.put(PrefHelper.USER_ID, token!!.toString(), this@KakaoSignupActivity)
                redirectMainActivity(token!!)
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                redirectLoginActivity()
            }

            override fun onNotSignedUp() {
            }

            override fun onFailure(errorResult: ErrorResult?) {
                val errorCode = ErrorCode.valueOf(errorResult?.errorCode)
                if (errorCode == ErrorCode.CLIENT_ERROR_CODE) {
                    finish()
                } else {
                    redirectLoginActivity()
                }
            }
        })
    }

    private fun showSignUp() {
    }

    private fun redirectMainActivity(token: Long) {
        disposable = networkService.requestLogin(Login.Request(token.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            Log.i(TAG, "login check 결과 $response")
                            if (response.result == "exist") {
                                val nextIntent = Intent(this@KakaoSignupActivity, MainActivity::class.java)
                                nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                                startActivity(nextIntent)
                                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                                finish()
                            } else {
                                startActivity(Intent(this@KakaoSignupActivity, KidInfoActivity::class.java))
                                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                                finish()
                            }
                        },
                        { failure ->
                            Log.i(TAG, "failure : ${failure.printStackTrace()}")
                        }
                )
    }

    private fun redirectLoginActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
