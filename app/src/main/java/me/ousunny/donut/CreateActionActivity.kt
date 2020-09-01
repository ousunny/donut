package me.ousunny.donut

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_action.*
import me.ousunny.donut.db.MacroDbTable
import java.io.IOException

class CreateActionActivity : AppCompatActivity() {

    private var imageBitmap: Bitmap? = null
    private val CHOOSE_IMAGE_REQUEST = 1
    private var position = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_action)

        tv_action.visibility = View.INVISIBLE
        var params= tv_action.layoutParams
        tv_action.alpha = 0.5f

        sb_action_size?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                params.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    p0.progress.toFloat(), resources.displayMetrics).toInt()
                params.height = params.width

                tv_action.layoutParams = params
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("AAAA", p0?.progress.toString())
            }

            override fun onStopTrackingTouch(p0: SeekBar) {
                Log.d("AAAA", p0?.progress.toString())
            }
        })

        val listener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.x = motionEvent.rawX - view.width / 2
                view.y = motionEvent.rawY - view.height / 2
                position = "${view.x},${view.y}"
            }
            true
        }

        tv_action.setOnTouchListener(listener)


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
                group_action_btn.visibility = View.INVISIBLE
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

    fun saveAction(view: View) {
        val intent = Intent(this, ViewMacro::class.java)
        val action = Action(
            "Refresh",
            position = "1.0,5.0"
        )
        intent.putExtra("action", action)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun toggleOverlay(view: View) {
         if (group_action_btn.visibility == View.INVISIBLE) {
             tv_action.visibility = View.VISIBLE
             group_action_btn.visibility = View.VISIBLE
         } else {
            tv_action.visibility = View.INVISIBLE
             group_action_btn.visibility = View.INVISIBLE
         }
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