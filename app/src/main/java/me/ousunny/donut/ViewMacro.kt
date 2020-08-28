package me.ousunny.donut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
            rv_macro.adapter = ActionsAdapter(this, data.actions)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_action) {
            val intent = Intent(this, CreateActionActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}