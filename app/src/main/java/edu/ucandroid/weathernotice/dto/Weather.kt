package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName


data class Weather(
        @SerializedName("country_code") var countryCode: String,
        @SerializedName("city_name") var city: String,
        @SerializedName("ob_time") var dateAndTime: String,
        @SerializedName("lat") var latitute: String,
        @SerializedName("lon") var longitude: String,
        //no humidity provided?
        @SerializedName("sunrise") var sunrise: String,
        @SerializedName("sunset") var sunset: String,
        @SerializedName("pres") var pressure: String,
        @SerializedName("wind_spd") var windSpeed: String,
        @SerializedName("temp") var tempCelsius: String
        //do we need precipitation, description?
        ) {

    override fun toString(): String {
        return "$countryCode $city"
    }

}

