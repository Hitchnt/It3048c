package edu.ucandroid.weathernotice.dao

import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.dto.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherDAO {
    @GET("/v2.0/current?&city=Cincinnati&country=USA&key=66af4499735e4bcc914957a0354de0b2")
    fun getAllWeather(): Call<ArrayList<WeatherInfo>>

    //Not yet implemented
    @GET("/v2.0/current")
    fun getWeather(@Query("city") WeatherCity: String, @Query("country") WeatherCountry: String, @Query("key") WeatherKey: String = "66af4499735e4bcc914957a0354de0b2") : Call<ArrayList<Weather>>
}