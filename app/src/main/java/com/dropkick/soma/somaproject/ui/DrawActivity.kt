package com.dropkick.soma.somaproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_draw.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DrawActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DrawActivity"
    }

    val networkService by lazy {
        AppController.retrofitCreate()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        paintCanvas.paintModule = PaintModule()

        eraserBtn.setOnClickListener{
            paintCanvas.setEraserMode()
            eraserBtn.setBackgroundResource(R.drawable.eraser2)
            penBtn.setBackgroundResource(R.drawable.pen)
        }

        penBtn.setOnClickListener{
            paintCanvas.setPenMode()
            eraserBtn.setBackgroundResource(R.drawable.eraser)
            penBtn.setBackgroundResource(R.drawable.pen1)
        }

        questionBtn.setOnClickListener {
            if (questionTextView.visibility == View.INVISIBLE) {
                questionTextView.visibility = View.VISIBLE
            } else {
                questionTextView.visibility = View.INVISIBLE
            }
            questionTextView.text = "자신이 생각하는 가장 아름다운 집을 그려보세요"
        }

        sendBtn.setOnClickListener {
            val uri = paintCanvas.convertToUri()
            val file = File(uri!!.path)
            val userId = RequestBody.create(MediaType.parse("multipart/form-data"), "userId_test")
            val identifier = RequestBody.create(MediaType.parse("multipart/form-data"), "identify")
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("imgdata", file.name, requestFile)
            disposable = networkService.uploadPhoto(userId, identifier, body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> Log.i(TAG, "result : ${result.result.message}") },
                        { failure -> Log.i(TAG, "failure : ${failure.printStackTrace()}") }
                    )
            tempImageView.setImageURI(uri)
        }

        redColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorRed)) }
        yellowColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorYellow)) }
        greenColorBtn.setOnClickListener { paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorGreen)) }
        blueColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorBlue)) }
        blackColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorBlack)) }

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
