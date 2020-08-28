package me.ousunny.donut

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_action.*

class CreateActionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_action)

        iv_screenshot.setImageResource(R.drawable.ic_launcher_foreground)
        
    }

}