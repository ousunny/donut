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
import kotlinx.android.synthetic.main.single_action.view.*
import kotlinx.android.synthetic.main.single_macro.view.*

class ActionsAdapter(val context: Context, val actions: List<Action>): RecyclerView.Adapter<ActionsAdapter.ActionViewHolder>() {

    class ActionViewHolder(val card: View): RecyclerView.ViewHolder(card)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsAdapter.ActionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_action, parent, false)
        return ActionsAdapter.ActionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActionsAdapter.ActionViewHolder, position: Int) {


        if (holder != null) {
            Log.d("AAAA", actions[1].title)
            holder.card.tv_action_title.text = actions[position].title

        }
    }

    override fun getItemCount(): Int = actions.count()
}