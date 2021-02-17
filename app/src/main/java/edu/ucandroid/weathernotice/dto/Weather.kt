package edu.ucandroid.weathernotice.dto

//import com.google.gson.annotations.SerializedName

data class Weather ( var temp: String){
    override fun toString(): String {
        return temp
    }
}

