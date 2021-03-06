package edu.ucandroid.weathernotice.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import edu.ucandroid.weathernotice.R
import kotlinx.android.synthetic.main.rowlayout.view.*

class RecyclerAdapter ( var userList: ArrayList<Weatherinfo>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rowlayout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminders = userList[position]
        holder.bind(reminders)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bind(userinfo: Weatherinfo) {
            itemView.lblEventInfo.text = userinfo.city
            itemView.lblEventInfo2.text = userinfo.temperature + "℃"
            itemView.lblEventInfo3.text = userinfo.time


        }
    }


}