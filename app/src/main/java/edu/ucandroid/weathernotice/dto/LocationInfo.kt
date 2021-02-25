package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName


data class LocationInfo(
        @SerializedName("country_code") var countryCode: String,
        @SerializedName("city_name") var city: String,
        @SerializedName("lon") var lon: String?,
        @SerializedName("lat") var lat: String?) {

    override fun toString(): String {
        return "$countryCode $city"
    }

}
