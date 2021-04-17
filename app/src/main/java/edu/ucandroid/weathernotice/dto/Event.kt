package edu.ucandroid.weathernotice.dto

data class Event(var city: String = "", var weather: String = "") {

    override fun toString(): String {
        return "$city $weather"
    }
}
