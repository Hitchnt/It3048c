package edu.ucandroid.weathernotice.service

import androidx.lifecycle.MutableLiveData
import edu.ucandroid.weathernotice.dao.IWeatherDAO
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.ui.main.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherService {

    fun fetchAllWeather() : MutableLiveData<ArrayList<Weather>> {
        val weather = MutableLiveData<ArrayList<Weather>>()
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
                weather.value = response.body()
            }

        })
        return weather
    }

    fun fetchWeatherByLocation(locationName: String) : MutableLiveData<ArrayList<Weather>> {
        val weather = MutableLiveData<ArrayList<Weather>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IWeatherDAO::class.java)
        val call = service?.getWeather(locationName)
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
                weather.value = response.body()
            }

        })
        return weather
    }

}