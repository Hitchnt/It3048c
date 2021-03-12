package edu.ucandroid.weathernotice.dao

import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ILocationDAO {
    @GET("/ahmu83/38865147cf3727d221941a2ef8c22a77/raw/c647f74643c0b3f8407c28ddbb599e9f594365ca/US_States_and_Cities.json")
    fun getAllWeather(): Call<ArrayList<LocationInfo>>

    @GET("/Lwdthe1/81818d30d23f012628aac1cdf672627d/raw/45dc8bee7b4fc349ec87931100e0f258bb59f8ea/usaCities.js")
    fun getWeather(@Query("Name") WeatherName: String) : Call<ArrayList<LocationInfo>>
}