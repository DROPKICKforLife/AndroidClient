package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.util.TextUtils
import kotlinx.android.synthetic.main.activity_test_list.*
import kotlinx.android.synthetic.main.layout_test_list_element.view.*
import java.util.*

class TestListActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {


    companion object {
        val TAG = "TestListActivity"
    }
    private val testDataList: MutableList<TestListData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list)
        (1..5).mapTo(testDataList) { TestListData("불안 증세 치료 $it", "항아리 안에 물고기 가족을 그려보세요", R.drawable.test_image_example) }

        viewPager.adapter = TestListAdapter()
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(this)
        viewPager.clipToPadding = false
        totalPageNumberView.text = "/${TextUtils.convertNumToAddZeroText(testDataList.size, 2)}"
        titleTextView.text = "1 번째 치료"
        currPageNumberView.text = TextUtils.convertNumToAddZeroText(1, 2)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        titleTextView.text = "${position + 1} 번째 치료"
        currPageNumberView.text = TextUtils.convertNumToAddZeroText(position + 1, 2)
    }

    private inner class TestListAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View?, obj: Any?): Boolean = obj === view
        override fun getCount(): Int = testDataList.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(R.layout.layout_test_list_element, null)
            val testData = testDataList[position]

            view.testTitleView.text = testData.testTitle
            view.testSubTitleView.text = testData.testSubTitle
            view.testImageView.setImageResource(testData.testImageRes)
            container.addView(view, ViewGroup.LayoutParams(298, 460))
            return view
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as View)
        }

    }
    data class TestListData(val testTitle: String, val testSubTitle: String, val testImageRes: Int)
}
