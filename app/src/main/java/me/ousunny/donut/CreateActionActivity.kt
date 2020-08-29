package me.ousunny.donut

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_action.*
import java.io.IOException

class CreateActionActivity : AppCompatActivity() {

    private var imageBitmap: Bitmap? = null
    private val CHOOSE_IMAGE_REQUEST = 1
//    private var position = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_action)

//        var listener = View.OnTouchListener(function = { view, motionEvent ->
//            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
//                view.x = motionEvent.rawX - view.width / 2
//                view.y = motionEvent.rawY - view.height / 2
//                position = "${view.x},${view.y}"
//            }
//            true
//        })

//        tv_position.setOnTouchListener(listener)


        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        supportActionBar?.hide()

        iv_screenshot.setImageResource(R.drawable.ic_launcher_foreground)

    }

    fun chooseImage(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val bitmap = tryReadBitmap(data.data)
            
            bitmap?.let {
                this.imageBitmap = bitmap

                iv_screenshot.setImageBitmap(bitmap)
                btn_choose_image.visibility = View.INVISIBLE
            }
        }
    }

    private fun tryReadBitmap(data: Uri?): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch(e: IOException) {
            e.printStackTrace()
            null
        }

    }

    fun toggleOverlay(view: View) {
         if (btn_choose_image.visibility == View.INVISIBLE)
             btn_choose_image.visibility = View.VISIBLE
         else
             btn_choose_image.visibility = View.INVISIBLE
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }
}