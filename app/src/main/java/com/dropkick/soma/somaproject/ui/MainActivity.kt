package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.util.MarginItemDecoration
import com.dropkick.soma.somaproject.util.PrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_list_element.view.*

class MainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    companion object {
        val TAG = "MainActivity"
        const val MAX_OFFSET = 390
        const val WEEK_COUNT = "weekCount"
    }

    private val networkService by lazy {
        AppController.retrofitCreate()
    }

    private val disposable: CompositeDisposable = CompositeDisposable()
    private var weekCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(emptyList())
        recyclerView.addItemDecoration(MarginItemDecoration())

        appBarLayout.addOnOffsetChangedListener(this)
        disposable.add(networkService.getWeeksProgramInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result ->
                    recyclerView.adapter = RecyclerAdapter(result.result)
                    recyclerView.adapter.notifyDataSetChanged()

                },{ failure ->
                    Log.i(TAG, "${failure.message}")
                })
        )

        disposable.add(networkService.getProgramStatus(PrefHelper.getId(this@MainActivity))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result ->
                    Log.i(TAG, "$result")
                    (recyclerView.adapter as RecyclerAdapter).lockCount = result.week_id
                    weekCount = result.week_id
                    recyclerView.adapter.notifyDataSetChanged()
                    if (result.result == "0") {
                        resultButton.backgroundTintList = ContextCompat.getColorStateList(this@MainActivity, R.color.grayFont)
                    }
                }, {
                    failure ->
                    Log.i(TAG, "$failure")
                })
        )

        resultButton.setOnClickListener {
            Log.i(TAG, "resultButton onClick")
            val nextIntent = Intent(this@MainActivity, ResultActivity::class.java)
            nextIntent.putExtra(WEEK_COUNT, weekCount)
            startActivity(nextIntent)
        }


    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val opacity = (255 * Math.max(0f, (MAX_OFFSET + verticalOffset)/ MAX_OFFSET.toFloat())).toInt()
        bannerTextView.setTextColor(Color.argb(opacity, 0, 0, 0))
    }

    inner private class RecyclerAdapter(val dataList: List<String>) : RecyclerView.Adapter<ViewHolder>() {
        var lockCount = dataList.size
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_main_list_element, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bind(dataList[position], position, lockCount)
        }

        override fun getItemCount(): Int = dataList.size


    }


    inner private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String, pos: Int, lockCount: Int) {
            itemView.weekTextView.text = "WEEK${pos + 1}"
            if (pos < lockCount) {
                itemView.titleTextView.text = title
                itemView.setOnClickListener {
                    val intent = Intent(this@MainActivity, TestListActivity::class.java)
                    intent.putExtra("weekCount", pos + 1)
                    this@MainActivity.startActivity(intent)
                }
            } else {
                itemView.titleTextView.text = "아직 이용하실 수 없습니다."
                itemView.setOnClickListener(null)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }

    private var backPressedTime: Long = 0
    private val FINISH_INTERVAL_TIME = 2000
    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 1..FINISH_INTERVAL_TIME) {
            this@MainActivity.finishAffinity()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
