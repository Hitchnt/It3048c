package edu.ucandroid.weathernotice.service

import androidx.lifecycle.MutableLiveData
import edu.ucandroid.weathernotice.dao.ILocationDAO
import edu.ucandroid.weathernotice.dao.IWeatherDAO
import edu.ucandroid.weathernotice.dto.LocationInfo
//import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.ui.main.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationService {

    fun fetchLocation() : MutableLiveData<ArrayList<LocationInfo>> {
        var _locationInfo = MutableLiveData<ArrayList<LocationInfo>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(ILocationDAO::class.java)
        val call = service?.getAllWeather()
        call?.enqueue(object : Callback<ArrayList<LocationInfo>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ArrayList<LocationInfo>>, t: Throwable) {
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
                call: Call<ArrayList<LocationInfo>>,
                response: Response<ArrayList<LocationInfo>>
            ) {
                _locationInfo.value = response.body()
            }

        })
        return _locationInfo
    }

/*    fun fetchLocation(locationName: String) : MutableLiveData<ArrayList<LocationInfo>> {
        var _weather = MutableLiveData<ArrayList<LocationInfo>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(ILocationDAO::class.java)
        val call = service?.getAllWeather()
        call?.enqueue(object : Callback<ArrayList<LocationInfo>> {
            *//**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *//*
            override fun onFailure(call: Call<ArrayList<LocationInfo>>, t: Throwable) {
                val j = 1 + 1
                val i = 1 + 1
                val failMessage="Something went wrong"
            }

            *//**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             *//*
            override fun onResponse(
                call: Call<ArrayList<LocationInfo>>,
                response: Response<ArrayList<LocationInfo>>
            ) {
                _weather.value = response.body()
            }

        })
        return _weather
    }*/

}