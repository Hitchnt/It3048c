package edu.ucandroid.weathernotice.dto

import com.google.gson.annotations.SerializedName

data class Reminder(
@SerializedName("City")var city: String="", @SerializedName("Message") var massage : String="") {

    override fun toString(): String {
        return "$city $massage"
    }

}