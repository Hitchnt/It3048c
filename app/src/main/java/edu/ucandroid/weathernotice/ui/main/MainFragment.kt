package edu.ucandroid.weathernotice.ui.main

import android.Manifest
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import edu.ucandroid.weathernotice.MainActivity
import edu.ucandroid.weathernotice.R
import edu.ucandroid.weathernotice.dto.Reminder
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainFragment : Fragment() {

    private var user : FirebaseUser? = null
    private val AUTH_REQUEST_CODE = 2002

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val LOCATION_REQUEST_ID = 100

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //Not yet implemented
        /* val queue = Volley.newRequestQueue(getActivity());
         val url = "https://api.weatherbit.io/v2.0/current?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA";
         val urltmrw = "https://api.weatherbit.io/v2.0/forecast/hourly?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA&hours=24";
         val urlomrw = "https://api.weatherbit.io/v2.0/forecast/hourly?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA";
         val sdf_to = SimpleDateFormat("EEEE");
         val sdf_from = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");*/


        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //set observer of location info for autocomplete
        viewModel.locationinfos.observe(this, androidx.lifecycle.Observer { locationinfos ->
            enterCityname.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, locationinfos))

        })

        viewModel.fetchLocations()

        btnLocation.setOnClickListener {
            getLocation()
            readFireStoreData()
        }
        btnSave.setOnClickListener {
            saveString()
        }
        btnSearch.setOnClickListener {
            if (enterCityname.text != null)
                GetWeatherForLocation(enterCityname.text.toString())
        }
        btnEdit.setOnClickListener {
            //to set a reminder
            timepickerDialog()
        }
        btnMore.setOnClickListener {
            numberPickerCustom()
        }
        btnList.setOnClickListener {
            (activity as MainActivity).onSwipeLeft()
        }
        btnLogin.setOnClickListener {
            logon()
        }

        btnCompareMenu.setOnClickListener {
            var popup2 = PopupMenu(context, btnCompareMenu)
            popup2.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.compare1 -> {
                        tCompare.text = ">"
                        true
                    }
                    R.id.compare2 -> {
                        tCompare.text = "<"
                        true
                    }
                    else -> false
                }
            }
            popup2.inflate(R.menu.mune_compare)
            popup2.show()
        }

        btnWeatherMenu.setOnClickListener {
            var popup = PopupMenu(context, btnWeatherMenu)
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.weather1 -> {
                        tWeather.text = "sunny"
                        true
                    }
                    R.id.weather2 -> {
                        tWeather.text = "rainy"
                        true
                    }
                    R.id.weather3 -> {
                        tWeather.text = "thunderstorm"
                        true
                    }
                    R.id.weather4 -> {
                        tWeather.text = "windy"
                        true
                    }
                    R.id.weather5 -> {
                        tWeather.text = "cloudy"
                        true
                    }
                    else -> false
                }
            }
            popup.inflate(R.menu.menu_weather)
            popup.show()
        }
        switchTemp.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                showTemp()
            } else {
                hideTemp()
            }
        }
        switchWeather.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                showWeather()
            } else {
                hideWeather()
            }
        }
    }
    fun  showWeather(){
        tWeather.visibility = View.VISIBLE
        tWeatheris.visibility = View.VISIBLE
        btnWeatherMenu.visibility = View.VISIBLE
    }

    fun hideWeather(){
        tWeatheris.visibility = View.GONE
        tWeather.visibility = View.GONE
        btnWeatherMenu.visibility = View.GONE
    }
    fun showTemp(){
        tTempCondition.visibility = View.VISIBLE
        tCompare.visibility = View.VISIBLE
        btnCompareMenu.visibility = View.VISIBLE
        tTemperature.visibility = View.VISIBLE
        btnMore.visibility = View.VISIBLE
    }

    fun hideTemp(){
        tTempCondition.visibility = View.GONE
        tCompare.visibility = View.GONE
        btnCompareMenu.visibility = View.GONE
        tTemperature.visibility = View.GONE
        btnMore.visibility = View.GONE

    }


    fun numberPickerCustom() {
        val d = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.number_picker_dialog, null)
        d.setTitle("Choose temperature")
        d.setView(dialogView)
        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.dialog_number_picker)
        numberPicker.maxValue = 80
        numberPicker.minValue = 0
        numberPicker.wrapSelectorWheel = false
        numberPicker.setOnValueChangedListener { numberPicker, i, i1 -> tTemperature.text = "$i1" }
        d.setPositiveButton("Done") { dialogInterface, i ->
                println("onClick: " + numberPicker.value)
        }
        d.setNegativeButton("Cancel") { dialogInterface, i -> }
        val alertDialog = d.create()
        alertDialog.show()
    }

    private fun timepickerDialog() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            tTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(context, R.style.TimePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }


    private fun GetWeatherForLocation(cityCountryName: String) {
        val queue = Volley.newRequestQueue(getActivity());
        //split city name and country code for URL
        val cityCountrySplit = cityCountryName.split(", ")
        val cityName = cityCountrySplit[0]
        val countryCode = cityCountrySplit[1]
        val url = "https://api.weatherbit.io/v2.0/current?key=66af4499735e4bcc914957a0354de0b2&city=$cityName&country=$countryCode";
        val sdf_to = SimpleDateFormat("EEEE")
        val sdf_from = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    //set weather dat to UI fields
                    var typeText: String
                    val data = response.getJSONArray("data").getJSONObject(0);
                    if (data.getDouble("precip") != 0.0) {
                        typeText = "Rainy"
                    } else if (data.getInt("clouds") > 50) {
                        typeText = "Cloudy"
                    } else {
                        typeText = "Clear"
                    }
                    humidity.text = data.getString("rh").toString() + "%"
                    sunset.text = data.getString("sunset").toString()
                    sunrise.text = data.getString("sunrise").toString()
                    showTemperature.text = data.getString("temp").toString() + "º Celsius"
                    showCountry.text = data.getString("country_code").toString()
                    showCity.text = data.getString("city_name").toString()
                    pressure.text = data.getString("pres").toString() + " millibars"
                    windSpeed.text = data.getString("wind_spd").toString() + " m/s"


                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", error.toString())
                })
        queue.add(request);

    }



    private fun logon() {
        var providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
                //,AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), AUTH_REQUEST_CODE
        )
    }

    private fun saveString() {
        if(user == null) {
            logon()
        }
        else{
        saveFireStore(
                showCity.text.toString()
                ,tCompare.text.toString()
                ,tTemperature.text.toString()
                ,tTime.text.toString()
                ,tWeather.text.toString()
                ,"Hey it happening"
                ,FirebaseAuth.getInstance().currentUser.email)
        // viewModel.save(reminder,user!!)
        //rcyEvents.adapter?.notifyDataSetChanged()
         }
    }


    fun saveFireStore(city: String
                      ,inequality: String
                      ,temperature: String
                      ,alertTime: String
                      ,typeOfWeather:String
                      ,message: String
                      ,user: String){
        val db = FirebaseFirestore.getInstance()
        val account:MutableMap<String, Any> = HashMap()
        account["City"]= city
        account["inequality"]= inequality
        account["Temperature"]= temperature
        account["AlertTime"]= alertTime
        account["TypeOfWeather"]= typeOfWeather
        account["Message"]= message
        account["UserID"]= user
        db.collection("Reminders")
            .add(account)
            .addOnSuccessListener {
                Toast.makeText(activity, "record added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "record added", Toast.LENGTH_SHORT).show()
            }
         //readFireStoreData()
    }


    fun readFireStoreData(){
        var userFirebaseData = ArrayList<Reminder>()
        // Create a reference to the cities collection
        val dba = FirebaseFirestore.getInstance()
        val citiesRef = dba.collection("Reminders")
        val query = citiesRef.whereEqualTo("UserId", "tyor455@gmail.com")
        /** The following query returns all the capital cities: */
        val capitalCities = dba.collection("UserID").whereEqualTo("capital", true)

        dba.collection("Reminders")
                .whereEqualTo("UserID", "tyor455@gmail.com")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        var thisthing= Reminder(
                                city = document.data["City"].toString(),
                                state = document.data["State"].toString(),
                                temperature = document.data["Temperature"].toString(),
                                alertTime = document.data["AlertTime"].toString(),
                                typeOfWeather = document.data["TypeOfWeather"].toString(),
                                message = document.data["Message"].toString(),
                                userID = document.data["UserID"].toString()
                        )
                        userFirebaseData.add(thisthing)
                    }
                }.addOnSuccessListener {

                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }



    }

    fun adamslogic(userFirebaseData: ArrayList<Reminder>) {


    }


    fun getLocation() {

        if (checkForLocationPermission()) {
            updateLocation()
        } else {
            askLocationPermission()
        }
    }

    fun updateLocation() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        locationRequest.interval = 60000
        locationRequest.fastestInterval = 5000

        mFusedLocationProviderClient = FusedLocationProviderClient(context!!)


        if (ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED)

        {

            return
        }
        mFusedLocationProviderClient.requestLocationUpdates(
                locationRequest, mLocationCallback,
                Looper.myLooper()
        )
    }


    var mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {

            var location: Location = p0.lastLocation

            updateAddressUI(location)

        }
    }

    fun updateAddressUI(location: Location) {


        var addressList = ArrayList<Address>()

        var geocoder: Geocoder = Geocoder(activity!!.applicationContext, Locale.getDefault())

        addressList = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
        ) as ArrayList<Address>
        /** this get the full address **/
        //enterCityname.setText(addressList.get(0).getAddressLine(0))
        /** this get the city only from address **/
        enterCityname.setText(addressList[0].locality + " , " + addressList[0].countryCode)



    }


    fun checkForLocationPermission(): Boolean {

        if (ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        )
            return true

        return false

    }


    fun askLocationPermission() {

        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_ID)


    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {

        if (requestCode == LOCATION_REQUEST_ID) {

            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLocation()
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AUTH_REQUEST_CODE){
            user = FirebaseAuth.getInstance().currentUser
        }
    }


}


