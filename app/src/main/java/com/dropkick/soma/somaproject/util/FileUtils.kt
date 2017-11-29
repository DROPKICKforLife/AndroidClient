package com.dropkick.soma.somaproject.util

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by junhoe on 2017. 11. 24..
 */
object FileUtils {

    fun createImageFile(context: Context) : File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val root = context.getDir("draw_image_dir", Context.MODE_PRIVATE).absolutePath
        val myDir = File("$root/Img")
        if (!myDir.exists()) {
            myDir.mkdirs()
        }

        return File.createTempFile(imageFileName,".jpg", myDir.absoluteFile)
    }
}