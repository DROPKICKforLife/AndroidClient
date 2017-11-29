package com.dropkick.soma.somaproject.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by junhoe on 2017. 11. 22..
 */
class MarginItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.set(15, 5, 15, 5)
    }
}