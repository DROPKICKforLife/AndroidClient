package com.dropkick.soma.somaproject.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.R

/**
 * Created by junhoe on 2017. 11. 26..
 */
class KidInfoDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_kid_info_detail, container, false)
        return view
    }
}