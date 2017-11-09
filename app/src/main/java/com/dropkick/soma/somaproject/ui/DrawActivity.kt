package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dropkick.soma.somaproject.R
import kotlinx.android.synthetic.main.activity_draw.*

class DrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        paintCanvas.paintModule = PaintModule()
        eraserBtn.setOnClickListener{ paintCanvas.switchEraserMode() }
    }
}
