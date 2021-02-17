package edu.ucandroid.weathernotice.dao

import edu.ucandroid.weathernotice.dto.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherDAO {
    @GET("/v2.0/current?&city=Cincinnati&country=USA&key=66af4499735e4bcc914957a0354de0b2")
    fun getAllWeather(): Call<ArrayList<Weather>>

    @GET("/v2.0/current?&city=Cincinnati&country=USA&key=66af4499735e4bcc914957a0354de0b2")
    fun getWeather(@Query("Name") WeatherName: String) : Call<ArrayList<Weather>>
}