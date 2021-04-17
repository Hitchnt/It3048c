package edu.ucandroid.weathernotice.dto

import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.SerializedName

data class Reminder(
@SerializedName("City")var city: String="",
@SerializedName("State")var state: String="",
@SerializedName("Temperature")var temperature: String="",
@SerializedName("Inequality")var inequality: String="",
@SerializedName("AlertTime")var alertTime: String="",
@SerializedName("TypeOfWeather")var typeOfWeather: String="",
@SerializedName("Message") var message : String="",
@SerializedName("UserID")var userID: String=""){

    private var _events: ArrayList<Event> = ArrayList<Event>()

    var events : ArrayList<Event>
        @Exclude get() {return _events}
        set(value) {_events = value}

    override fun toString(): String {
        return "$city $message $temperature"
    }

}