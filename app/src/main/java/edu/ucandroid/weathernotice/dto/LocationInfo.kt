package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName


data class LocationInfo(
       // @SerializedName("country_code") var countryCode: String,
        @SerializedName("city") var city: String)
       // @SerializedName("lon") var lon: String?,
       // @SerializedName("lat") var lat: String?)
       {

    override fun toString(): String {
        return "$city"
    }

}
