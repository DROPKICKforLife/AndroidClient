package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.R
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.fragment_kid_info_base.view.*
import kotlinx.android.synthetic.main.layout_question_list_element.view.*
import kotlinx.android.synthetic.main.layout_question_list_header.view.*

class QuestionActivity : AppCompatActivity() {

    companion object {
        const val TAG = "QuestionActivity"
    }
    val questionDataList: MutableList<QuestionListData> = mutableListOf()

    init {
        questionDataList.add(QuestionListData("", -2))
        questionDataList.add(QuestionListData("1. 집에는 누가 살고 있나요", 0))
        questionDataList.add(QuestionListData("2. 이 집의 분위기는 어떤가요?", 0))
        questionDataList.add(QuestionListData("3. 이 집에서 살고 싶나요?", 0))
        questionDataList.add(QuestionListData("", -1))
    }

    data class QuestionListData(val question: String, val viewType: Int)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        recyclerView.adapter = QuestionListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    inner private class QuestionListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
            0 -> {
                val view = layoutInflater.inflate(R.layout.layout_question_list_element, parent, false)
                QuestionListViewHolder(view)
            }
            -1 -> {
                val view = layoutInflater.inflate(R.layout.layout_question_list_footer, parent, false)
                FooterHolder(view)
            }
            -2 -> {
                val view = layoutInflater.inflate(R.layout.layout_question_list_header, parent, false)
                HeaderHolder(view)
            }
            else -> throw RuntimeException()
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder?.itemViewType == 0) {
                (holder as QuestionListViewHolder).bind(questionDataList[position])
            } else if (holder?.itemViewType == -2) {
                (holder as HeaderHolder).bind("1. 집")
            }
        }

        override fun getItemCount(): Int = questionDataList.size

        override fun getItemViewType(position: Int): Int = questionDataList[position].viewType

    }

    inner private class QuestionListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: QuestionListData) {
            itemView.questionTextView.text = item.question
        }

    }

    inner private class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String) {
            itemView.titleTextView.text = title;
        }
    }

    inner private class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.nextButton.setOnClickListener {

            }
        }
    }
}

