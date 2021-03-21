package edu.ucandroid.weathernotice.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.service.WeatherService
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var weatherLocations: MutableLiveData<ArrayList<Weather>> = MutableLiveData<ArrayList<Weather>>()
    var weatherService: WeatherService = WeatherService()

    init{
        fetchWeatherLocations("e")
    }

    // TODO: Implement the ViewModel
    fun fetchWeatherLocations() {
        weatherLocations = weatherService.fetchAllWeather();
    }

    fun fetchWeatherLocations(locationName: String) {
        weatherLocations = weatherService.fetchWeather(locationName);
    }
}