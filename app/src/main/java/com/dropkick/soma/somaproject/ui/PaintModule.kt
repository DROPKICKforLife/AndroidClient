package com.dropkick.soma.somaproject.ui

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.Log

/**
 * Created by junhoe on 2017. 11. 9..
 */
class PaintModule {

    companion object {
        private const val TAG = "PaintModule"
    }
    var paint = Paint()
    var currColor: Int = Color.BLACK
        set(value) {
            field = value
            Log.i(TAG, "color is : $value")
            paint = initPaint()
        }

    init {
        paint = initPaint()
    }

    fun setEraserMode() {
        paint = initPaint()
        paint.apply {
            color = Color.TRANSPARENT
            strokeWidth = 60f
            style = Paint.Style.STROKE
            maskFilter = null
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            isAntiAlias = true
        }
    }

    fun setPenMode() {
        paint = initPaint()
    }

    private fun initPaint(): Paint {
        val paint = Paint()
        paint.apply {
            isAntiAlias = true
            isDither = true
            color = currColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 12f
        }
        return paint
    }

}