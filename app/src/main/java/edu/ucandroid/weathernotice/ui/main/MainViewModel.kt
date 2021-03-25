package edu.ucandroid.weathernotice.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.dto.WeatherInfo
import edu.ucandroid.weathernotice.service.LocationService
import edu.ucandroid.weathernotice.service.WeatherService

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel



    var locationinfos: MutableLiveData<ArrayList<LocationInfo>> = MutableLiveData()
    var locationService: LocationService = LocationService()
    var weatherinfos: MutableLiveData<ArrayList<WeatherInfo>> = MutableLiveData()
    var weatherService: WeatherService = WeatherService()
    init{
        fetchLocations()
    }

    // TODO: Implement the ViewModel


    fun fetchLocations() {
        locationinfos = locationService.fetchLocation()


    }

}