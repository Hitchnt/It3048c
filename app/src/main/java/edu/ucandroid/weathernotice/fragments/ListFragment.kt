package edu.ucandroid.weathernotice.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import edu.ucandroid.weathernotice.MainActivity
import edu.ucandroid.weathernotice.R
import edu.ucandroid.weathernotice.dto.Event
import edu.ucandroid.weathernotice.dto.Reminder
import edu.ucandroid.weathernotice.ui.main.MainViewModel
import edu.ucandroid.weathernotice.ui.main.RecyclerAdapter
import edu.ucandroid.weathernotice.ui.main.Weatherinfo
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.ArrayList


class ListFragment : Fragment() {
    lateinit var weatherList: ArrayList<Weatherinfo>
    lateinit var testList: ArrayList<Reminder>


    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }
        btnAdd.setOnClickListener {
            (activity as MainActivity).onSwipeRight()
        }

        LoadRecycerFromFireBase()
    }


    private fun LoadRecycerFromFireBase() {
        rcyEvents.hasFixedSize()
        rcyEvents.layoutManager = LinearLayoutManager(context)
        rcyEvents.itemAnimator = DefaultItemAnimator()


        val userFirebaseData = ArrayList<Reminder>()
        // Create a reference to the cities collection
        val dba = FirebaseFirestore.getInstance()

        weatherList = arrayListOf<Weatherinfo>()

        dba.collection("Reminders")
            .whereEqualTo("UserID", FirebaseAuth.getInstance().currentUser.email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    var thisthing= Reminder(
                        city = document.data["City"].toString(),
                        temperature = document.data["Temperature"].toString(),
                        alertTime = document.data["AlertTime"].toString(),
                    )
                    userFirebaseData.add(thisthing)
                    weatherList.add(Weatherinfo(document.data["City"].toString(), document.data["Temperature"].toString(),document.data["AlertTime"].toString()))
                }
            }.addOnSuccessListener {
                val recyclerAdapter = RecyclerAdapter(weatherList)

                rcyEvents.apply {
                    adapter = recyclerAdapter

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }


    }

}


