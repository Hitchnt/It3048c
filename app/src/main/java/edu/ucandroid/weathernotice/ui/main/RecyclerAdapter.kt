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
        val movie = userList[position]
        holder.bind(movie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bind(userinfo: Weatherinfo) {
            itemView.lblEventInfo.text = userinfo.temperature
           // itemView.imgWeatherinList.setImageDrawable(R.drawable.ic_add.toDrawable())

            //itemView.ratingTextView.text = userinfo.rating.toString()
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }
}