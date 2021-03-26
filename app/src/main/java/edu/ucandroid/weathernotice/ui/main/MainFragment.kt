package edu.ucandroid.weathernotice.ui.main
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import edu.ucandroid.weathernotice.R
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*
import kotlin.collections.ArrayList
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import edu.ucandroid.weathernotice.dto.Reminder
import edu.ucandroid.weathernotice.dto.Weather
import java.text.SimpleDateFormat
import kotlin.collections.HashMap

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainFragment : Fragment() {

    private var user : FirebaseUser? = null
    private val AUTH_REQUEST_CODE = 2002

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val locationRequestId = 100

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val queue = Volley.newRequestQueue(getActivity());
        val url = "https://api.weatherbit.io/v2.0/current?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA";
        val urltmrw = "https://api.weatherbit.io/v2.0/forecast/hourly?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA&hours=24";
        val urlomrw = "https://api.weatherbit.io/v2.0/forecast/hourly?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA";

        val sdf_to = SimpleDateFormat("EEEE");
        val sdf_from = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    var typeText : String;
                    val data = response.getJSONArray("data").getJSONObject(0);
                    if(data.getDouble("precip") != 0.0) { typeText = "Rainy"; }
                    else if(data.getInt("clouds") > 50) { typeText = "Cloudy"; }
                    else { typeText = "Clear"; }

                    //backgroundImage.setImageResource(if (typeText == "Rainy") R.drawable.ic_rainy_bg else (if (typeText == "Cloudy") R.drawable.ic_clear_bg else R.drawable.ic_sunny_bg));

                    sunset.text = data.getString("sunset").toString();
                    sunrise.text = data.getString("sunrise").toString();
                    showTemperature.text = data.getString("temp").toString() + "º";

                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", error.toString());
                });

        // there surely is a better way to do is but it's 3am and i cba


        queue.add(request);

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        btnLocation.setOnClickListener {

            getLocation()

        }
        btnSave.setOnClickListener {
            saveString()
        }
        btnSearch.setOnClickListener {
            GetWeatherForLoocation()
        }

    }


    private fun GetWeatherForLoocation() {
        val url = "https://api.weatherbit.io/v2.0/current?key=66af4499735e4bcc914957a0354de0b2&city=Cincinnati&country=USA";
        val sdf_to = SimpleDateFormat("EEEE");
        val sdf_from = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    var typeText : String;
                    val data = response.getJSONArray("data").getJSONObject(0)
                    if(data.getDouble("precip") != 0.0) { typeText = "Rainy"; }
                    else if(data.getInt("clouds") > 50) { typeText = "Cloudy"; }
                    else { typeText = "Clear"; }

                    //backgroundImage.setImageResource(if (typeText == "Rainy") R.drawable.ic_rainy_bg else (if (typeText == "Cloudy") R.drawable.ic_clear_bg else R.drawable.ic_sunny_bg));

                    //showTemperature.text = typeText;
                    showTemperature.text = data.getString("temp").toString() + "º"
                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", error.toString())
                })
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
     /**

       if(user == null) {
       logon()
    }
      //  var reminder = Reminder().apply{
    var reminder = Reminder().apply {
        city = "citytest"
        massage="massagetest"
    }
        * */
        saveFireStore("input from users","just even more input from the la  user")
       // viewModel.save(reminder,user!!)

      //  }
       }


    fun saveFireStore(city:String,reminder:String){
        val db = FirebaseFirestore.getInstance()
        val Account:MutableMap<String,Any> = HashMap()
        Account["City"]= city
        Account["Message"]= reminder

        db.collection("Reminders")
            .add(Account)
            .addOnSuccessListener {
                Toast.makeText(activity,"record added",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
            Toast.makeText(activity,"record added",Toast.LENGTH_SHORT).show()
            }
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
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000

        mFusedLocationProviderClient = FusedLocationProviderClient( context!!)


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

        var geocoder: Geocoder
        var addressList = ArrayList<Address>()

        geocoder = Geocoder(activity!!.applicationContext, Locale.getDefault())

        addressList = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
        ) as ArrayList<Address>
        /** this get the full address **/
        //enterCityname.setText(addressList.get(0).getAddressLine(0))
        /** this get the city only from address **/
        enterCityname.setText(addressList[0].locality)



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
                locationRequestId)


    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {

        if (requestCode == locationRequestId) {

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


