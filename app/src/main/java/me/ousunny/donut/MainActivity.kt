package me.ousunny.donut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.ousunny.donut.db.MacroDbTable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()
//        iv_screenshot?.setImageResource(R.drawable.ic_launcher_foreground)
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)

        val macros = MacroDbTable(this).getAllMacros()
        if (macros.count() > 0)
            rv_main.adapter = MacrosAdapter(this, macros)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.macro_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_macro) {
            val intent = Intent(this, ViewMacro::class.java)
            startActivity(intent)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
    }
}