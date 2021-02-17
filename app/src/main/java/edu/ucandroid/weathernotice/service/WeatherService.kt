package edu.ucandroid.weathernotice.service

import androidx.lifecycle.MutableLiveData
import edu.ucandroid.weathernotice.dto.Weather


class WeatherService {

    fun fetchWeather(locationTemperature: String) : MutableLiveData<ArrayList<Weather>> {
        var _weather = MutableLiveData<ArrayList<Weather>>()


        return _weather
    }

}