package com.dropkick.soma.somaproject.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View

/**
 * Created by junhoe on 2017. 11. 9..
 */
class PaintCanvas: View, View.OnTouchListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    companion object {
        private const val TAG = "PaintCanvas"
        private const val TOUCH_TOLERANCE = 4f
    }

    var paintModule: PaintModule? = null
    var currPath: Path? = null
    var prevX: Float = 0f
    var prevY: Float = 0f

    private val pathList: MutableList<Pair<Path, Paint>> = ArrayList()

    init {
        setOnTouchListener(this)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathList.forEach { path -> canvas.drawPath(path.first, path.second) }
    }

    private fun startTouch(x: Float, y: Float) {
        currPath = Path()
        pathList.add(Pair(currPath!!, paintModule!!.paint))
        currPath?.reset()
        currPath?.moveTo(x, y)
        prevX = x
        prevY = y
    }

    private fun moveTouch(x: Float, y: Float) {
        val dx = Math.abs(x - prevX)
        val dy = Math.abs(y - prevY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            currPath?.quadTo(prevX, prevY, (x + prevX)/2, (y + prevY)/2)
            prevX = x
            prevY = y
        }
    }

    private fun endTouch() {
        currPath?.lineTo(prevX, prevY)
    }

    fun switchEraserMode() {
        paintModule?.switchEraserMode()
    }


    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            ACTION_DOWN -> {
                startTouch(event.x, event.y)
                invalidate()
            }

            ACTION_MOVE -> {
                moveTouch(event.x, event.y)
                invalidate()
            }

            ACTION_UP -> {
                endTouch()
                invalidate()
                currPath = Path()
                pathList.add(Pair(currPath!!, paintModule!!.paint))
            }
        }
        return true
    }
}