package me.ousunny.donut

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()
//        iv_screenshot?.setImageResource(R.drawable.ic_launcher_foreground)
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = MacrosAdapter(this, getSampleMacros())
    }
}