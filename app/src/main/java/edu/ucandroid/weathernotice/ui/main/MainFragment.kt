package edu.ucandroid.weathernotice.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import edu.ucandroid.weathernotice.R
import kotlinx.android.synthetic.main.main_fragment.*
import edu.ucandroid.weathernotice.dto.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.locationinfos.observe(this, Observer{
                locationinfos -> enterCityname.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, locationinfos))
        })

        viewModel.fetchLocations()

        btnSearch.setOnClickListener {
           // viewModel.weatherService.fetchWeather()
            showCity.text = viewModel.weatherinfos.toString()

        }

    }

}