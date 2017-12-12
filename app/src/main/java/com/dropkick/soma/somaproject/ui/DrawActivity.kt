package com.dropkick.soma.somaproject.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dropkick.soma.somaproject.AppController
import com.dropkick.soma.somaproject.R
import com.dropkick.soma.somaproject.util.PrefHelper
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
        const val BACKGROUND_IMAGE_PATH = "backgroundImageUrl"
        const val IMAGE_ID = "imageID"
        const val TITLE = "title"
    }

    val networkService by lazy {
        AppController.retrofitCreate()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        paintCanvas.paintModule = PaintModule()

        eraserBtn.setOnClickListener {
            paintCanvas.setEraserMode()
            eraserBtn.setBackgroundResource(R.drawable.eraser2)
            penBtn.setBackgroundResource(R.drawable.pen)
        }

        penBtn.setOnClickListener {
            paintCanvas.setPenMode()
            eraserBtn.setBackgroundResource(R.drawable.eraser)
            penBtn.setBackgroundResource(R.drawable.pen1)
        }

        questionTextView.text = intent.getStringExtra(TITLE)
        Glide.with(this@DrawActivity).load(intent.getStringExtra(BACKGROUND_IMAGE_PATH)).apply(RequestOptions().centerCrop())
                .into(backgroundImageView)
        questionBtn.setOnClickListener {
            if (questionTextView.visibility == View.INVISIBLE) {
                questionTextView.visibility = View.VISIBLE
            } else {
                questionTextView.visibility = View.INVISIBLE
            }
        }
        sendBtn.setOnClickListener {
            val backgroundPath = intent.getStringExtra(BACKGROUND_IMAGE_PATH)
            if (backgroundPath != null) {
                Glide.with(this@DrawActivity).asBitmap().load(backgroundPath).apply(RequestOptions().centerCrop()).into( object : SimpleTarget<Bitmap>(backgroundImageView.width, backgroundImageView.height) {
                    override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                        uploadPhoto(resource)
                    }
                })
            } else {
                uploadPhoto(null)
            }
        }

        redColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorRed)) }
        yellowColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorYellow)) }
        greenColorBtn.setOnClickListener { paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorGreen)) }
        blueColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorBlue)) }
        blackColorBtn.setOnClickListener{ paintCanvas.setPenColor(ContextCompat.getColor(this, R.color.penColorBlack)) }

    }

    private fun uploadPhoto(backgroundBitmap: Bitmap?) {
        val uri = paintCanvas.convertToUri(backgroundBitmap)
        val file = File(uri!!.path)
        val userId = RequestBody.create(MediaType.parse("multipart/form-data"), PrefHelper.getId(this@DrawActivity))
        val identifier = RequestBody.create(MediaType.parse("multipart/form-data"), intent.getStringExtra(IMAGE_ID))
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("imgdata", file.name, requestFile)
        disposable = networkService.uploadPhoto(userId, identifier, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.i(TAG, "result : ${result.result.message}")
                            val intent = Intent(this@DrawActivity, QuestionActivity::class.java)
                            intent.putExtra(IMAGE_ID, getIntent().getStringExtra(IMAGE_ID))
                            startActivity(intent)
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                        }, { failure -> Log.i(TAG, "failure : ${failure.printStackTrace()}") }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
