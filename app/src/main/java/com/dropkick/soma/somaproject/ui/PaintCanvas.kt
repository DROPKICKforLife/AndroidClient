package com.dropkick.soma.somaproject.ui

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.dropkick.soma.somaproject.util.FileUtils
import java.io.FileOutputStream

/**
 * Created by Jun Hoe on 2017. 11. 9..
 * 유저가 선택한 도구 (여러 가지 색이 들어있는 펜, 지우개)를 통하여 그림을 그릴 수 있는 환경을 제공하는 뷰
 * 지속적으로 그려지는 경로를 저장해두었다가 비트맵 이미지를 형성, 크기에 맞게 리사이징하여 서버에 전송한다.
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

    fun setEraserMode() {
        paintModule?.setEraserMode()
    }

    fun setPenMode() {
        paintModule?.setPenMode()
    }

    fun setPenColor(color: Int) {
        paintModule?.currColor = color
    }

    fun convertToUri(backgroundBitmap: Bitmap?) : Uri? {
        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        backgroundBitmap?.run {
            val paint = Paint()
            paint.isFilterBitmap = true
            val rect = Rect(0, 0, width, height)
            canvas.drawBitmap(backgroundBitmap, null, rect, paint)
        }
        draw(canvas)
        val file = FileUtils.createImageFile(context)
        val fout =  FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, fout)
        fout.flush()

        return Uri.fromFile(file)
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