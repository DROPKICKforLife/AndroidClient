package com.dropkick.soma.somaproject.ui.kakao

import android.content.Context
import android.util.AttributeSet
import com.dropkick.soma.somaproject.R
import com.kakao.usermgmt.LoginButton

/**
 * Created by junhoe on 2017. 11. 30..
 */
class KakaoCustomButton : LoginButton {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        inflate(context, R.layout.kakao_login_layout, this)
    }
}