package me.ousunny.donut

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_macro.view.*

class MacrosAdapter(val context: Context, val macros: List<Macro>): RecyclerView.Adapter<MacrosAdapter.MacroViewHolder>() {

    class MacroViewHolder(val card: View): RecyclerView.ViewHolder(card)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacrosAdapter.MacroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_macro, parent, false)
        return MacroViewHolder(view)
    }

    override fun onBindViewHolder(holder: MacroViewHolder, position: Int) {


        if (holder != null) {
            holder.card.tv_macro_title.text = macros[position].title
            holder.card.setOnClickListener{
                val intent = Intent(context, ViewMacro::class.java)

                intent.putExtra("data", macros[position])

                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int = macros.count()
}