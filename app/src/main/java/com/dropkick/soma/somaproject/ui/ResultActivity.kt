package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.network.data.ResultData
import com.dropkick.soma.somaproject.util.PrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.layout_result_list_element.view.*

class ResultActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ResultActivity"
    }
    private val networkService by lazy {
        AppController.retrofitCreate()
    }

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        titleTextView.text = "검사 결과 및 치료 진단"
        recyclerView.adapter = ResultAdapter(listOf())
        disposable.add(networkService.getDoctorResult(PrefHelper.getId(this@ResultActivity), intent.getIntExtra(MainActivity.WEEK_COUNT, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response -> recyclerView.adapter = ResultAdapter(response.result)
                }, {
                    failure -> Log.i(TAG, "$failure")
                })
        )
        recyclerView.layoutManager = LinearLayoutManager(this@ResultActivity)
    }

    inner class ResultAdapter(private val resultData: List<String>) : RecyclerView.Adapter<ResultHolder>() {
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
        fun bind(data: String) {
            itemView.contentTextView.text = data
        }
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }
}
