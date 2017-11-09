package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dropkick.soma.somaproject.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, TestListActivity::class.java))
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        }, 3000)
    }
}
