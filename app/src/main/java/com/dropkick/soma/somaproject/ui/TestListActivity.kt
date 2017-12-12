package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.network.data.TestListData
import com.dropkick.soma.somaproject.util.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test_list.*
import kotlinx.android.synthetic.main.layout_test_list_element.view.*

class TestListActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {


    companion object {
        val TAG = "TestListActivity"
    }

    val networkService by lazy {
        AppController.retrofitCreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(this)
        viewPager.clipToPadding = false

        titleTextView.text = "1 번째 그림"
        currPageNumberView.text = TextUtils.convertNumToAddZeroText(1, 2)

        networkService.getTestList(intent.getIntExtra("weekCount", 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response -> viewPager.adapter = TestListAdapter(response.result)
                    totalPageNumberView.text = "/${TextUtils.convertNumToAddZeroText(response.result.size, 2)}"
                }, {
                    failure ->
                    Log.i(TAG, "$failure")
                })
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        titleTextView.text = "${position + 1} 번째 그림"
        currPageNumberView.text = TextUtils.convertNumToAddZeroText(position + 1, 2)
    }

    private inner class TestListAdapter(val dataList: List<TestListData.Element>) : PagerAdapter() {

        override fun isViewFromObject(view: View?, obj: Any?): Boolean = obj === view
        override fun getCount(): Int = dataList.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(R.layout.layout_test_list_element, null)
            val testData = dataList[position]

            view.testTitleView.text = testData.topic
            view.testSubTitleView.text = testData.content
            Log.i(TAG, "delete background image text view : $position")
            if (testData.url != null) {
                view.blankImageTextView.visibility = View.GONE
                Glide.with(this@TestListActivity).load(testData.url).into(view.testImageView)
            }
            view.startBtn.setOnClickListener {
                val intent = Intent(this@TestListActivity, DrawActivity::class.java)
                intent.putExtra(DrawActivity.BACKGROUND_IMAGE_PATH, testData.url)
                intent.putExtra(DrawActivity.IMAGE_ID, testData.key)
                intent.putExtra(DrawActivity.TITLE, testData.content)
                startActivity(intent)
            }
            container.addView(view, ViewGroup.LayoutParams(298, 460))
            return view
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as View)
        }

    }
}
