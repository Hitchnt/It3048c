package edu.ucandroid.weathernotice.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import edu.ucandroid.weathernotice.R
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val locationRequestId = 100

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {


        return inflater.inflate(R.layout.main_fragment, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        btnLocation.setOnClickListener {

            getLocation()
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
        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        locationRequest.interval = 60000
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
        enterCityname.setText(addressList.get(0).getAddressLine(0))
        //enterCityname.text= addressList.get(0).getAddressLine(0)


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

}