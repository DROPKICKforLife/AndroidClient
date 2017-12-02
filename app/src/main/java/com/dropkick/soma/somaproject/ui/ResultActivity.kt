package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.R
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.layout_result_list_element.view.*

class ResultActivity : AppCompatActivity() {

    data class ResultData(val title: String, val text: String)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        titleTextView.text = "김준회 님의 HTP 검사 결과"
        recyclerView.adapter = ResultAdapter(listOf(ResultData("집 검사 결과 진단", "이 아이는 커서 큰 사람이 될 것입니다."),
                ResultData("", ""), ResultData("", "")))
        recyclerView.layoutManager = LinearLayoutManager(this@ResultActivity)
    }

    inner class ResultAdapter(val resultData: List<ResultData>) : RecyclerView.Adapter<ResultHolder>() {
        override fun onBindViewHolder(holder: ResultHolder?, position: Int) {
            holder?.bind(resultData[position])
        }

        override fun getItemCount(): Int = resultData.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ResultHolder {
            val view = layoutInflater.inflate(R.layout.layout_result_list_element, parent, false)
            return ResultHolder(view)
        }

    }

    inner class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: ResultData) {
            itemView.resultTitleTextView.text
            itemView.contentTextView
        }
    }
}
