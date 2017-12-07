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
        recyclerView.adapter = ResultAdapter(listOf(ResultData("나무 검사 결과 진단",
                "뿌리를 강조한 것으로 보았을 때 내담자의 상태는 불안하고 결핍함을 알 수 있습니다. 일반적인 사람들은 나무 그림에 뿌리를 그리지 않는 편입니다. 뿌리를 통해 나무가 넘어질 까 염려하는 마음이 드러납니다."),
                ResultData("집 검사 결과 진단", "창문은 세상을 내다보고, 또 세상과 타인이 집안을 들여다 볼 수 있는 통로입니다. 때문에 이는 대인관계와 관련된 피검자의 주관적인 경험, 자기 혹은 자기대상이 환경과 상호 작용할 수 있는 능력에 대해 스스로 느끼는 감정들과 관련될 수 있습니다. 문과 창문을 그리지 않는 사람은 정신분열증으로 보아야 하며 주의 깊은 관찰이 요망됩니다."), ResultData("", "")))
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
            itemView.resultTitleTextView.text = data.title
            itemView.contentTextView.text = data.text
        }
    }
}
