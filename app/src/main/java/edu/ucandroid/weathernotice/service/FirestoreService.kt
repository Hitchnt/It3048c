package edu.ucandroid.weathernotice.service

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import edu.ucandroid.weathernotice.dto.Reminder

class FirestoreService {

    private lateinit var firestore : FirebaseFirestore

    fun save(reminder: Reminder, user: FirebaseUser) {
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