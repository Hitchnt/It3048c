package edu.ucandroid.weathernotice.dao

import edu.ucandroid.weathernotice.dto.LocationInfo
import edu.ucandroid.weathernotice.dto.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ILocationDAO {
    @GET("/lutangar/cities.json/master/cities.json")
    fun getAllLocations(): Call<ArrayList<LocationInfo>>

}