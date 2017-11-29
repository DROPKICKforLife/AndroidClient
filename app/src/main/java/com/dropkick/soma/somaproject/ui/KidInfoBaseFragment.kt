package com.dropkick.soma.somaproject.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.util.ExclusiveButtonGroup
import kotlinx.android.synthetic.main.fragment_kid_info_base.*
import kotlinx.android.synthetic.main.fragment_kid_info_base.view.*

/**
 * Created by junhoe on 2017. 11. 25..
 */
class KidInfoBaseFragment : Fragment() {

    companion object {
        const val TAG = "KidInfoBaseFragment"
    }
    var sex = "male"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_kid_info_base, container, false)
        ExclusiveButtonGroup()
                .addButton(view?.boyButton as Button, "boy", R.drawable.step1_boy, R.drawable.step1_boy_no)
                .addButton(view.girlButton as Button, "girl", R.drawable.step1_girl, R.drawable.step1_girl_no)
                .setSelectListener { _, key ->
                    sex = key
                }

        view.nextButton.setOnClickListener {
            (container as ViewPager).setCurrentItem(1, true)
        }

        return view
    }
}