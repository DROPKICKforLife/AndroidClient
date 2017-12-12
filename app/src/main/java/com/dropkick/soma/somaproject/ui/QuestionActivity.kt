package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.network.data.QuestionData
import com.dropkick.soma.somaproject.util.PrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.fragment_kid_info_base.view.*
import kotlinx.android.synthetic.main.layout_question_list_element.view.*
import kotlinx.android.synthetic.main.layout_question_list_header.view.*

class QuestionActivity : AppCompatActivity() {

    companion object {
        const val TAG = "QuestionActivity"
        const val HEADER_TYPE = -1
        const val FOOTER_TYPE = -2
        const val ELEMENT_TYPE = 0
    }


    val networkService by lazy {
        AppController.retrofitCreate()
    }

    var disposable: CompositeDisposable = CompositeDisposable()

    val totalDataList: MutableList<Pair<String, Int>> = mutableListOf()
    var replyDataList: MutableList<String> = mutableListOf()
    var questionDataList: MutableList<String> = mutableListOf()

    init {
        totalDataList.add(Pair("", HEADER_TYPE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        disposable.add(networkService.getQuestionList(PrefHelper.getId(this@QuestionActivity), intent.getStringExtra(DrawActivity.IMAGE_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response ->
                    response.result.forEach {
                        question -> totalDataList.add(Pair(question, ELEMENT_TYPE))
                        questionDataList.add(question)
                        replyDataList.add("")
                    }
                    totalDataList.add(Pair("", FOOTER_TYPE))
                    recyclerView.adapter = QuestionListAdapter(totalDataList)
                },{
                    failure -> Log.i(TAG, "$failure")
                })
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    inner private class QuestionListAdapter(val questionList: List<Pair<String, Int>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
            ELEMENT_TYPE -> {
                val view = layoutInflater.inflate(R.layout.layout_question_list_element, parent, false)
                QuestionListViewHolder(view)
            }
            FOOTER_TYPE -> {
                val view = layoutInflater.inflate(R.layout.layout_question_list_footer, parent, false)
                FooterHolder(view)
            }
            HEADER_TYPE -> {
                val view = layoutInflater.inflate(R.layout.layout_question_list_header, parent, false)
                HeaderHolder(view)
            }
            else -> throw RuntimeException()
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder?.itemViewType == ELEMENT_TYPE) {
                (holder as QuestionListViewHolder).bind(questionList[position].first, position - 1)
            } else if (holder?.itemViewType == HEADER_TYPE) {
                (holder as HeaderHolder).bind("알맞은 질의응답에 대한 답변을 적어주세요.")
            }
        }

        override fun getItemCount(): Int = totalDataList.size

        override fun getItemViewType(position: Int): Int = totalDataList[position].second

    }

    inner private class QuestionListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String, elemPos: Int) {
            itemView.questionTextView.text = item
            itemView.questionEdit.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    replyDataList[elemPos] = s?.toString().orEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

    }

    inner private class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String) {
            itemView.titleTextView.text = title
        }
    }

    inner private class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.nextButton.setOnClickListener {
                disposable.add(networkService.sendQuestionResponse(
                        QuestionData.QuestionSendData(PrefHelper.getId(this@QuestionActivity), questionDataList, replyDataList, intent.getStringExtra(DrawActivity.IMAGE_ID)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            response ->
                            Log.i(TAG, "$response")
                            if (response.result == "success") {
                                val nextIntent = Intent(this@QuestionActivity, TestListActivity::class.java)
                                nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(nextIntent)
                            }
                        }, {
                            failure -> Log.i(TAG, "$failure")
                        })
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }
}

