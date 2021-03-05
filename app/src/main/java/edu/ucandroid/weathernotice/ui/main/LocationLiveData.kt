package edu.ucandroid.weathernotice.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationRequest
import edu.ucandroid.weathernotice.dto.LocationDetails

class LocationLiveData(context: Context) : LiveData<LocationDetails>() {

    companion object {
        val locationRequest : LocationRequest = LocationRequest.create()

        // make location request :)
    }
}