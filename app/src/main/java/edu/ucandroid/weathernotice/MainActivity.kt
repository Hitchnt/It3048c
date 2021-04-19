package edu.ucandroid.weathernotice

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import edu.ucandroid.weathernotice.dto.Reminder
import edu.ucandroid.weathernotice.fragments.Fragment
import edu.ucandroid.weathernotice.fragments.ListFragment
import edu.ucandroid.weathernotice.ui.main.MainFragment
import edu.ucandroid.weathernotice.ui.main.MainViewModel
import edu.ucandroid.weathernotice.utilities.NotificationUtilities
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private  lateinit var detector: GestureDetectorCompat
    private lateinit var listFragment: ListFragment
    private lateinit var mainFragment: MainFragment
    private val mNotificationTime = Calendar.getInstance().timeInMillis
    private var mNotified = false
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
        detector = GestureDetectorCompat(this,WeatherGestureListener())
    }
    fun notifyStuff(fireBaseInfo: ArrayList<Reminder>){
        var notificationInfo: ArrayList<Reminder> = fireBaseInfo
        var notificationTimeToGo: String
        var i=0;
      //  notificationInfo = mainFragment.readFireStoreData()
        //Testing to ensure time gets passed for notification time first
        //notificationTimeToGo = notificationInfo[3].toString()
        //for (i < notificationInfo.size; i++){


       // }
       /* notificationInfo.forEach(){
        if(it.alertTime.isNotEmpty()){
            var alertTimeHour = it.alertTime.substringBefore(":")
            var alertTimeMin = it.alertTime.substringAfter(":")
            var alertTimeinMillis = (alertTimeHour.toInt() * 60 + alertTimeMin.toInt()) * 60 * 1000
            NotificationUtilities().setNotification(alertTimeinMillis.toLong(), this@MainActivity)
            }
        }*/
        notificationInfo.forEach {
            if (it.alertTime.isNotEmpty()) {

                val time = it.alertTime;
                val sdf =  SimpleDateFormat("H:mm");
                val dateObj = sdf.parse(time);
                val milliseconds = dateObj.time

                NotificationUtilities().setNotification(milliseconds.toLong(), this@MainActivity)
            }
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
    internal fun onSwipeLeft() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, listFragment)
            .commitNow()
    }
    //swipe right to show the main screen
    internal fun onSwipeRight(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commitNow()
    }
}