package edu.ucandroid.weathernotice.dao

import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ILocationDAO {
    @GET("/Lwdthe1/81818d30d23f012628aac1cdf672627d/raw/45dc8bee7b4fc349ec87931100e0f258bb59f8ea/usaCities.js")
    fun getAllWeather(): Call<ArrayList<LocationInfo>>

    @GET("/Lwdthe1/81818d30d23f012628aac1cdf672627d/raw/45dc8bee7b4fc349ec87931100e0f258bb59f8ea/usaCities.js")
    fun getWeather(@Query("Name") WeatherName: String) : Call<ArrayList<LocationInfo>>
}