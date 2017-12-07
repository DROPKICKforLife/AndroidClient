package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.util.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_list_element.view.*

class MainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    companion object {
        val TAG = "MainActivity"
        const val MAX_OFFSET = 390
    }
    private val mainDataList: MutableList<MainData> = mutableListOf()

    init {
        mainDataList.add(MainData("집, 나무, 사람을 통한 아이의 정서 검진"))
        mainDataList.add(MainData("표정 선호도를 통한 정서 관리 및 치료"))
        mainDataList.add(MainData("사물 그림을 통한 아이의 불안 증세 치료"))
        mainDataList.add(MainData("집, 나무, 사람을 통한 아이의 정서 검진"))
        mainDataList.add(MainData("집, 나무, 사람을 통한 아이의 정서 검진"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = RecyclerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(MarginItemDecoration())

        appBarLayout.addOnOffsetChangedListener(this)

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val opacity = (255 * Math.max(0f, (MAX_OFFSET + verticalOffset)/ MAX_OFFSET.toFloat())).toInt()
        Log.i(TAG, "opacity : $opacity")
        bannerTextView.setTextColor(Color.argb(opacity, 0, 0, 0))
    }
    data class MainData(val title: String)

    inner private class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_main_list_element, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bind(mainDataList[position], position)
        }

        override fun getItemCount(): Int = mainDataList.size
    }


    inner private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MainData, pos: Int) {
            itemView.titleTextView.text = data.title
            itemView.weekTextView.text = "WEEK${pos + 1}"
            itemView.setOnClickListener {
                val intent = Intent(this@MainActivity, TestListActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
        }
    }
}
