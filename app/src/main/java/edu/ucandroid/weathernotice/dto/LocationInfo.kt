
package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName


data class LocationInfo(
        @SerializedName("country") var countryCode: String,
        @SerializedName("name") var city: String)
{

    override fun toString(): String {
        return "$city, $countryCode"
    }

}