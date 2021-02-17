package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName


data class Weather (
    @SerializedName("country_code")var countryCode: String, @SerializedName("city_name") var city : String) {

    override fun toString(): String {
        return "$countryCode $city"
    }

}

