package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.ui.kakao.KakaoSignupActivity
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_splash.*
import java.security.MessageDigest

class SplashActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SplashActivity"
    }
    private var callback: SessionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        val handler = Handler()
        handler.postDelayed({
            /*
            startActivity(Intent(this@SplashActivity, TestListActivity::class.java))
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
            */
            splashBearImageView.visibility = View.INVISIBLE
            loginButton.visibility = View.VISIBLE
        }, 3000)

        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.i(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.i(TAG, exception?.message)
        }

        override fun onSessionOpened() {
            Log.i(TAG, "on Session Opened")
            redirectSignupActivity()
        }

        private fun redirectSignupActivity() {
        val intent = Intent(this@SplashActivity, KakaoSignupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

    }
}
