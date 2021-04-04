package edu.ucandroid.weathernotice.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.service.WeatherService
import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Reminder
import edu.ucandroid.weathernotice.service.FirestoreService
import edu.ucandroid.weathernotice.service.LocationService

class MainViewModel : ViewModel() {

    var firestore: FirestoreService = FirestoreService()
    var locationinfos: MutableLiveData<ArrayList<LocationInfo>> = MutableLiveData()
    var locationService: LocationService = LocationService()
    var weatherLocations: MutableLiveData<ArrayList<Weather>> = MutableLiveData<ArrayList<Weather>>()
    var weatherService: WeatherService = WeatherService()



    fun fetchLocations() {
        locationinfos = locationService.fetchLocation()
    }

    fun fetchWeatherLocations() {
        weatherLocations = weatherService.fetchAllWeather()
    }

    fun fetchAllWeather() {
        weatherLocations = weatherService.fetchAllWeather()
    }

    fun fetchWeatherLocations(locationName: String) {
        weatherLocations = weatherService.fetchWeatherByLocation(locationName)
    }
    fun save(reminder: Reminder, user: FirebaseUser) {
        firestore.save(reminder, user)
    }

}