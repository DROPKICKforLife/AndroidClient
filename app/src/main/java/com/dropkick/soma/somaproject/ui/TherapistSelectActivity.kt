package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.dropkick.soma.somaproject.R
import kotlinx.android.synthetic.main.activity_therapist_select.*
import kotlinx.android.synthetic.main.layout_therapist_list_element.view.*

class TherapistSelectActivity : AppCompatActivity() {


    private val therapistDataList: MutableList<TherapistData> = mutableListOf()

    init {
        therapistDataList.add(TherapistData("김준회", "심리치료사 3급", 2013, "",
                "서울중앙심리상담센터", listOf("서울대학교 졸업", "1급 국가 공인 자격증", "중앙대 심리학과 외래교수", "중앙대 내과 명예교수")))
        therapistDataList.add(TherapistData("김준회", "심리치료사 3급", 2013, "",
                "서울중앙심리상담센터", listOf("서울대학교 졸업", "1급 국가 공인 자격증", "중앙대 심리학과 외래교수", "중앙대 내과 명예교수")))
        therapistDataList.add(TherapistData("김준회", "심리치료사 3급", 2013, "",
                "서울중앙심리상담센터", listOf("서울대학교 졸업", "1급 국가 공인 자격증", "중앙대 심리학과 외래교수", "중앙대 내과 명예교수")))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_therapist_select)
        recyclerView.adapter = RecyclerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this@TherapistSelectActivity)
    }

    inner private class RecyclerAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_therapist_list_element, parent, false)
            return RecyclerViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(therapistDataList[position])
        }

        override fun getItemCount(): Int = therapistDataList.size

    }

    inner private class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TherapistData) {
            //Glide.with(this@TherapistSelectActivity).
            //        load(item.profileImageURL).into(itemView.profileImageView)
            itemView.nameTextView.text = "${item.name} 심리상담사"
            itemView.belongTextView.text = item.belong
            itemView.countTextView.text = "총 ${item.count}명이 상담하였습니다."
            itemView.selectButton.setOnClickListener {  }
            itemView.belongHospitalTextView.text = item.belongHospital
            itemView.specListView.adapter = ArrayAdapter<String>(this@TherapistSelectActivity,
                    R.layout.layout_spec_list_element, item.specList)
            itemView.specListView.isEnabled = false
            itemView.specListView.divider = null
            val params = itemView.specListView.layoutParams
            params.height = 50 + therapistDataList.size * 30
            itemView.specListView.layoutParams = params
            itemView.setOnClickListener {
                if (itemView.detailInfoLayout.visibility == GONE) {
                    itemView.detailInfoLayout.visibility = VISIBLE
                    itemView.detailIndicator.visibility = VISIBLE
                    itemView.shortIndicator.visibility = GONE
                } else {
                    itemView.detailInfoLayout.visibility = GONE
                    itemView.detailIndicator.visibility = GONE
                    itemView.shortIndicator.visibility = VISIBLE
                }
                TransitionManager.beginDelayedTransition(recyclerView)
            }
        }
    }

    data class TherapistData(val name: String, val belong: String, val count: Int, val profileImageURL: String,
                             val belongHospital: String, val specList: List<String>)
}
