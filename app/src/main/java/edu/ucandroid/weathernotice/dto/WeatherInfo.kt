package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName

data class WeatherInfo(@SerializedName("wind_spd") var windSpeed: String,
                       @SerializedName("wind_dir") var windDirection: String,
                       @SerializedName("wind_cdir_full") var wind_cdir_full: String,
                       @SerializedName("temp") var temperature : String,
                       @SerializedName("description") var description : String,
                       @SerializedName("precip") var precipitation : String,
                       @SerializedName("sunrise") var sunrise: String,
                       @SerializedName("country_code") var countryCode: String,
                       @SerializedName("city_name") var city: String,
                       @SerializedName("lon") var lon: String,
                       @SerializedName("lat") var lat: String){

    override fun toString(): String {
        return "$temperature $windSpeed $windDirection $wind_cdir_full $description $sunrise $precipitation $countryCode $city $lon $lat"
    }

}
