package edu.ucandroid.weathernotice.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import edu.ucandroid.weathernotice.dao.IWeatherDAO
import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.dto.WeatherInfo
import edu.ucandroid.weathernotice.ui.main.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherService {

    fun fetchWeather() : MutableLiveData<ArrayList<WeatherInfo>> {
        var _weather = MutableLiveData<ArrayList<WeatherInfo>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IWeatherDAO::class.java)
        val call = service?.getAllWeather()
        call?.enqueue(object : Callback<ArrayList<WeatherInfo>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ArrayList<WeatherInfo>>, t: Throwable) {
                val failMessage="Something went wrong"
                Log.e("Weather Array List Call", failMessage, t)
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                    call: Call<ArrayList<WeatherInfo>>,
                    response: Response<ArrayList<WeatherInfo>>
            ) {
                _weather.value = response.body()
            }

        })
        return _weather
    }

    /*fun fetchWeather(locationName: String) : MutableLiveData<ArrayList<Weather>> {
        var _weather = MutableLiveData<ArrayList<Weather>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IWeatherDAO::class.java)
        val call = service?.getAllWeather()
        call?.enqueue(object : Callback<ArrayList<Weather>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ArrayList<Weather>>, t: Throwable) {
                val j = 1 + 1
                val i = 1 + 1
                val failMessage="Something went wrong"
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                    call: Call<ArrayList<Weather>>,
                    response: Response<ArrayList<Weather>>
            ) {
                _weather.value = response.body()
            }

        })
        return _weather
    }*/

}