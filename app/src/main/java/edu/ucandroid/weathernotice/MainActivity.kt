package edu.ucandroid.weathernotice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import edu.ucandroid.weathernotice.fragments.Fragment
import edu.ucandroid.weathernotice.fragments.ListFragment
import edu.ucandroid.weathernotice.ui.main.MainFragment
import edu.ucandroid.weathernotice.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class MainActivity : AppCompatActivity() {
    private  lateinit var detector: GestureDetectorCompat
    private lateinit var listFragment: ListFragment
    private lateinit var mainFragment: MainFragment
    private  var CHANNEL_ID= "channelis_example_01"
    private  var notificationId = 101
    var weatherDesc = ""
    var weatherTemp = ""
    var weatherPrecip = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        listFragment = ListFragment.newInstance()
        mainFragment = MainFragment.newInstance()
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
        createNotificationchannel()
        detector = GestureDetectorCompat(this,WeatherGestureListener())
        loop()
    }

     fun onDataPass(data: String) {
         if(data != "") {
             var dataSplit = data.split(",")
             weatherDesc = dataSplit[0]
             weatherTemp = dataSplit[1]
             weatherPrecip = dataSplit[2]
         }
    }

    private fun loop() {
        CoroutineScope(IO).
        launch {
            delay(10000)
            CoroutineScope(Main).launch {
                activefunction()
                loop()
            }
        }
    }
    private fun activefunction() {
        //Toast.makeText( this,"test", Toast.LENGTH_SHORT).show()
        //createNotificationchannel()
        if(mainFragment.weatherList != "") {
            sendNotification()
        }
    }

    private fun createNotificationchannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            val name = "noticication title"
            val descriptionText = "description Text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description=descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun sendNotification(){
        //make if test on weather status from API
        var notificationMessage = ""
        val currentTime = LocalTime.now()
        onDataPass(mainFragment.weatherList)
        if (weatherTemp.toDouble() <= 0){
            notificationMessage = "cover plants"
        }
        else if (weatherTemp.toDouble() > 0 && weatherPrecip.toDouble() > 0){
            notificationMessage = "wear a rain coat"
        }
        else{
            notificationMessage= "No notifications"
        }

        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(weatherDesc)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //if (mainFragment.remindTime == currentTime.toString()){
        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())
        //}
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent((event))){
            true
        }else{
            super.onTouchEvent(event)
        }
    }

    inner class WeatherGestureListener : GestureDetector.SimpleOnGestureListener(){

        private val SWIP_THRESHOLD = 250
        private val SWIP_VELOCITY_THRESHOLD = 250
        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return if (Math.abs(diffX) > Math.abs(diffY)){
                //when swipe parallel
                if(Math.abs(diffX) > SWIP_THRESHOLD && Math.abs(velocityX) > SWIP_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        this@MainActivity.onSwipeRight()
                    } else {
                        this@MainActivity.onSwipeLeft()
                    }
                    true
                }else{
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }else{
                //when swipe vertical
                if(Math.abs(diffY) > SWIP_THRESHOLD && Math.abs(velocityX) > SWIP_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        this@MainActivity.onSwipeTop()
                    } else {
                        this@MainActivity.onSwipeBotton()
                    }
                    true
                } else{
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }

        }
    }

    private fun onSwipeBotton() {
        Toast.makeText(this, "Botton Swip", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeTop() {
        Toast.makeText(this, "Top Swip", Toast.LENGTH_LONG).show()
    }
    //swipe left to show the list screen
    private fun onSwipeLeft() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, listFragment)
            .commitNow()
    }
    //swipe right to show the main screen
    private fun onSwipeRight(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commitNow()
    }
}