package me.ousunny.donut

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_macro.*
import java.util.*

class ViewMacro : AppCompatActivity() {

    var macros: Macro? = null

    val CREATE_ACTION_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_macro)

        rv_macro.setHasFixedSize(true)
        rv_macro.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        if (bundle != null)
            macros = bundle?.getParcelable("data") as Macro?
        else
            macros = Macro("Default title", mutableListOf())

        if (macros != null)
            rv_macro.adapter = ActionsAdapter(this, macros!!.actions)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_action) {
            val intent = Intent(this, CreateActionActivity::class.java)
            startActivityForResult(intent, CREATE_ACTION_REQUEST)
        } else {
            finish()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_ACTION_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val action = data.getParcelableExtra<Action>("action")
            action?.let { macros?.actions?.add(it) }

            rv_macro.adapter?.notifyItemInserted(macros?.actions?.count()!! - 1)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}