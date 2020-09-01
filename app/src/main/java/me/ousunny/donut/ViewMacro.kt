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
import me.ousunny.donut.db.MacroDbTable
import java.util.*

class ViewMacro : AppCompatActivity() {

    var macro: Macro? = null

    val CREATE_ACTION_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_macro)

        rv_macro.setHasFixedSize(true)
        rv_macro.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        if (bundle != null)
            macro = bundle?.getParcelable("data") as Macro?
        else
            macro = Macro("Default title", mutableListOf())

        if (macro != null)
            rv_macro.adapter = ActionsAdapter(this, macro!!.actions)
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
            action?.let { macro?.actions?.add(it) }

            macro?.let { MacroDbTable(this).store(it) }
            
            rv_macro.adapter?.notifyItemInserted(macro?.actions?.count()!! - 1)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}