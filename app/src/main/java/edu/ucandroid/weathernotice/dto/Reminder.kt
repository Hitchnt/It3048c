package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName

data class Reminder(
@SerializedName("City")var city: String="",
@SerializedName("State")var state: String="",
@SerializedName("Temperature")var temperature: String="",
@SerializedName("Inequality")var inequality: String="",
@SerializedName("AlertTime")var alertTime: String="",
@SerializedName("TypeOfWeather")var typeOfWeather: String="",
@SerializedName("Message") var message : String="",
@SerializedName("UserID")var userID: String="") {

    override fun toString(): String {
        return "$city $message"
    }

}