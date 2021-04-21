package edu.ucandroid.weathernotice.dto

import android.app.TimePickerDialog

data class Event(var city: String = "", var weather: String = "", var timePickerDialog: TimePickerDialog) {

    //del after
    override fun toString(): String {
        return "$city $weather $timePickerDialog"
    }
}
