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
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by junhoe on 2017. 11. 25..
 */
class KidInfoBaseFragment : Fragment() {

    companion object {
        const val TAG = "KidInfoBaseFragment"
    }
    var sexType = "0"
    var ability = "0"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_kid_info_base, container, false)
        ExclusiveButtonGroup()
                .addButton(view?.boyButton!!, "0", R.drawable.step1_boy, R.drawable.step1_boy_no)
                .addButton(view.girlButton, "1", R.drawable.step1_girl, R.drawable.step1_girl_no)
                .setSelectListener { _, key ->
                    sexType = key
                }

        ExclusiveButtonGroup()
                .addButton(view?.rankButton1, "0", R.drawable.step1_sang, R.drawable.step1_sang_no)
                .addButton(view.rankButton2 as Button, "1", R.drawable.step1_jong, R.drawable.step1_jong_no)
                .addButton(view.rankButton3 as Button, "2", R.drawable.step1_ha, R.drawable.step1_ha_no)
                .setSelectListener { _, key ->
                    ability = key
                }

        view.nextButton.setOnClickListener {
            val infoTable = (activity as KidInfoActivity).infoTable
            infoTable.put("childname", childNameEdit.text.toString())
            infoTable.put("childsex", sexType)
            infoTable.put("childability", ability)
            infoTable.put("childbirth", toTimeStamp(yearEdit.text.toString(),
                    monthEdit.text.toString(), dayEdit.text.toString()))
            (container as ViewPager).setCurrentItem(1, true)
        }

        return view
    }

    private fun toTimeStamp(year: String, month: String, day: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse("$year-$month-$day")
        val timeStamp = Timestamp(date.time).time
        Log.i(TAG, "$timeStamp")
        return timeStamp.toString()
    }
}