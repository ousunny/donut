package me.ousunny.donut

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_macro.*

class ViewMacro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_macro)

        rv_macro.setHasFixedSize(true)
        rv_macro.layoutManager = LinearLayoutManager(this)


        val bundle = intent.extras
        val data: Macro? = bundle?.getParcelable("data") as Macro?
        if (data != null) {
            Log.d("AAAA", data.actions.count().toString())
            rv_macro.adapter = ActionsAdapter(this, data.actions)
        }
    }
}