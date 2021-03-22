package edu.ucandroid.weathernotice.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.service.WeatherService
import com.google.firebase.firestore.FirebaseFirestore
import edu.ucandroid.weathernotice.dto.Reminder

class MainViewModel : ViewModel() {

    private lateinit var firestore : FirebaseFirestore

    var weatherLocations: MutableLiveData<ArrayList<Weather>> = MutableLiveData<ArrayList<Weather>>()
    var weatherService: WeatherService = WeatherService()

    init{
        fetchWeatherLocations("e")
        firestore = FirebaseFirestore.getInstance()
    }

    // TODO: Implement the ViewModel
    fun fetchWeatherLocations() {
        weatherLocations = weatherService.fetchAllWeather();
    }

    fun fetchWeatherLocations(locationName: String) {
        weatherLocations = weatherService.fetchWeather(locationName);
    }
    fun save(reminder: Reminder, user: FirebaseUser){
        val document = firestore.collection("weather").document()
        reminder.city = document.id
        val set = document.set(reminder)
        set.addOnSuccessListener {
            Log.d("Firebase", "document saved")
           /**
            if (reminder != null && reminder.size > 0) {
                saveReminders(reminder, user)
            }
            */
        }
        set.addOnFailureListener {
            Log.d("Firebase", "Save Failed")
        }

    }

}