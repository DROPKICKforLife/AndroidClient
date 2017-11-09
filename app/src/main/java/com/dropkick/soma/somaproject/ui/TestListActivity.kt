package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.util.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_test_list.*
import kotlinx.android.synthetic.main.layout_test_list_element.view.*
import java.util.*

class TestListActivity : AppCompatActivity() {

    private val testList: MutableList<TestListData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)
        (1..20).mapTo(testList) { TestListData(it.toString()) }

        recyclerView.adapter = TestListAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.addItemDecoration(
                GridSpacingItemDecoration((recyclerView.layoutManager as GridLayoutManager).spanCount, 20, true))
    }

    private inner class TestListAdapter : RecyclerView.Adapter<TestListViewHolder>() {

        override fun getItemCount(): Int = testList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestListViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_test_list_element, parent, false)
            val params = view.layoutParams as RecyclerView.LayoutParams
            val width = parent.measuredWidth
            params.height = Math.round(width / 2f)
            view.layoutParams = params
            return TestListViewHolder(view)
        }

        override fun onBindViewHolder(holder: TestListViewHolder, position: Int) {
            holder.testNameView.text = testList[position].testName
        }

    }

    private inner class TestListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val testNameView: TextView = itemView.testNameView
    }

    data class TestListData(val testName: String)
}
