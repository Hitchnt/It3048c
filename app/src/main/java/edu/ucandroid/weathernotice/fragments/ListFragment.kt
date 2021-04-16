package edu.ucandroid.weathernotice.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ucandroid.weathernotice.MainActivity
import edu.ucandroid.weathernotice.R
import edu.ucandroid.weathernotice.dto.Event
import edu.ucandroid.weathernotice.ui.main.MainViewModel
import kotlinx.android.synthetic.main.list_fragment.*


class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }
        btnAdd.setOnClickListener {
            (activity as MainActivity).onSwipeRight()
        }
        rcyEvents.hasFixedSize()
        rcyEvents.layoutManager = LinearLayoutManager(context)
        rcyEvents.itemAnimator = DefaultItemAnimator()
        //rcyEvents.adapter = EventAdapter(viewModel.locationinfos.events, R.layout.rowlayout)
    }
    
    inner class EventAdapter(val events: List<Event>, val itemLayout: Int) : RecyclerView.Adapter<ListFragment.EventViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
            return EventViewHolder(view)
        }


        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
            val event = events.get(position)
            holder.updateEvent(event)
        }

        override fun getItemCount(): Int {
            return events.size
        }

    }
    

    inner class EventViewHolder (itemVIew : View) : RecyclerView.ViewHolder(itemVIew) {
        private var imgWeatherinList : ImageView = itemView.findViewById(R.id.imgWeatherinList)
        private var lblEventInfo : TextView = itemVIew.findViewById(R.id.lblEventInfo)

        fun  updateEvent (event : Event){
            lblEventInfo.text = event.toString()
            
        }
    }




}