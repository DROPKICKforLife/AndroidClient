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
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.network.data.TheraphistData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_therapist_select.*
import kotlinx.android.synthetic.main.layout_therapist_list_element.view.*

class TherapistSelectActivity : AppCompatActivity() {


    companion object {
        const val TAG = "TherapistSelectActivity"
    }

    val networkService by lazy {
        AppController.retrofitCreate()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_therapist_select)
        disposable = networkService.requestDoctorProfile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ result ->
                        Log.i(TAG, "${result.result}")
                        recyclerView.adapter = RecyclerAdapter(result)
                    },{ failure ->
                        Log.i(TAG, "on Failure ${failure.message}")
                    })
        recyclerView.adapter = RecyclerAdapter(TheraphistData.Response(emptyList()))
        recyclerView.layoutManager = LinearLayoutManager(this@TherapistSelectActivity)
    }

    inner private class RecyclerAdapter(val result: TheraphistData.Response) : RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_therapist_list_element, parent, false)
            return RecyclerViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(result.result[position])
        }

        override fun getItemCount(): Int = result.result.size

    }

    inner private class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TheraphistData.DetailData) {

            Glide.with(this@TherapistSelectActivity).
                    load(item.profileimgurl).into(itemView.profileImageView)
            itemView.nameTextView.text = "${item.doctorname} 심리상담사"
            itemView.belongTextView.text = item.schoolname
            itemView.countTextView.text = "총 ${item.doctorcount}명이 상담하였습니다."
            itemView.selectButton.setOnClickListener {  }
            itemView.belongHospitalTextView.text = item.hospitalname
            itemView.specListView.adapter = ArrayAdapter<String>(this@TherapistSelectActivity,
                    R.layout.layout_spec_list_element, item.speclist)
            itemView.specListView.isEnabled = false
            itemView.specListView.divider = null
            val params = itemView.specListView.layoutParams
            params.height = 50 + item.speclist.size * 30
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

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
