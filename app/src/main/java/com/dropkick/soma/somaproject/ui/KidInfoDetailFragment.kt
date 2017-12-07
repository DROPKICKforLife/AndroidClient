package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.network.data.UserData
import com.dropkick.soma.somaproject.util.PrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_kid_info_detail.*
import kotlinx.android.synthetic.main.fragment_kid_info_detail.view.*

/**
 * Created by junhoe on 2017. 11. 26..
 */
class KidInfoDetailFragment : Fragment() {

    companion object {
        const val TAG = "KidInfoDetailFragment"
    }

    val networkService by lazy {
        AppController.retrofitCreate()
    }

    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_kid_info_detail, container, false)
        view.nextButton.setOnClickListener {
            val infoTable = (activity as KidInfoActivity).infoTable
            infoTable["prevcontent"] = receiveEdit.text.toString()
            infoTable["hopecontent"] = wantEdit.text.toString()
            disposable = networkService.postUserInfo(UserData.Request(PrefHelper.getString(PrefHelper.USER_TOKEN, activity), infoTable["childname"].orEmpty(),
                    infoTable["childsex"]!!.toInt(), infoTable["childability"]!!.toInt(), infoTable["childbirth"].orEmpty(),
                    infoTable["prevcontent"].orEmpty(), infoTable["hopecontent"].orEmpty()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        response ->
                        Log.i(TAG, PrefHelper.getString(PrefHelper.USER_TOKEN, activity))
                        infoTable.entries.forEach {
                            entry -> Log.i(TAG, "value : ${entry.value}")
                        }
                        if (response.result == "success") {
                            startActivity(Intent(activity, TherapistSelectActivity::class.java))
                        }
                    }, {
                        failure -> Log.i(TAG, "Failure : ${failure.printStackTrace()}")
                    })
        }
        return view
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}