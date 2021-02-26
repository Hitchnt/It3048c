package edu.ucandroid.weathernotice.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.service.LocationService
import edu.ucandroid.weathernotice.service.WeatherService

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel



    var locationinfos: MutableLiveData<ArrayList<LocationInfo>> = MutableLiveData<ArrayList<LocationInfo>>()
    var locationService: LocationService = LocationService()
    init{
        fetchLocations("e")
    }

    // TODO: Implement the ViewModel


    fun fetchLocations() {
        locationinfos = locationService.fetchAllLocation();
    }

    fun fetchLocations(locationName: String) {
        locationinfos = locationService.fetchLocation(locationName);
    }
}