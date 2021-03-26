package edu.ucandroid.weathernotice

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import edu.ucandroid.weathernotice.fragments.Fragment
import edu.ucandroid.weathernotice.fragments.ListFragment
import edu.ucandroid.weathernotice.ui.main.MainFragment
import edu.ucandroid.weathernotice.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private  lateinit var detector: GestureDetectorCompat
    private lateinit var listFragment: ListFragment
    private lateinit var mainFragment: MainFragment

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent((event))){
            true
        }else{
            super.onTouchEvent(event)
        }
    }

    inner class WeatherGestureListener : GestureDetector.SimpleOnGestureListener(){

        private val SWIP_THRESHOLD = 200
        private val SWIP_VELOCITY_THRESHOLD = 200
        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return if (Math.abs(diffX) > Math.abs(diffY)){
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

    private fun onSwipeLeft() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, listFragment)
                .commitNow()
    }

    private fun onSwipeRight(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commitNow()
    }
}