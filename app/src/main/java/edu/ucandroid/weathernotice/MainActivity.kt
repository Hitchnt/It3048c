package edu.ucandroid.weathernotice

//import edu.ucandroid.weathernotice.service.NotificationService.Companion.CHANNEL_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import edu.ucandroid.weathernotice.dto.Reminder
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private  lateinit var detector: GestureDetectorCompat
    private lateinit var listFragment: ListFragment
    private lateinit var mainFragment: MainFragment
    private  var notificationId = 101
    private  var CHANNEL_ID= "channelis_example_01"
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
        detector = GestureDetectorCompat(this, WeatherGestureListener())

        loop()
 }


    private fun createNotificationchannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            val name = "notification title"
            val descriptionText = "description Text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description=descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun sendNotification(fireBaseInfo: ArrayList<Reminder>){
        var notificationInfo: ArrayList<Reminder> = fireBaseInfo
        createNotificationchannel()
        var i : Long = 0


            notificationInfo.forEach {
                val time = it.alertTime;
                val sdf =  SimpleDateFormat("H:mm");
                val dateObj = sdf.parse(time);


                var timenow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))


            if(timenow == time){


                    i++
                    var notificationTitle = "Your Weather Notification: " + it.alertTime
                    var notificationMessage = ""

                    if (it.alertTime == null || it.alertTime.contains("_")) {
                        return@forEach
                    }
                    if (it.inequality != null || it.temperature != null || "_" !in it.temperature || "_" !in it.inequality) {
                        notificationMessage = "The temperature is " + it.inequality + " " + it.temperature + "° "
                    }
                    if (it.typeOfWeather != null || "_" !in it.typeOfWeather) {
                        notificationMessage = notificationMessage + "The weather is " + it.typeOfWeather
                    } else {
                        return@forEach
                    }
                    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationMessage)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    with(NotificationManagerCompat.from(this)) {
                        CoroutineScope(IO).launch {
                            delay(60000 )
                            CoroutineScope(Main).launch {
                                // activefunction()
                                notify(notificationId, builder.build())
                            }
                        }
                    }
                }
            }

    }



    private fun loop() {
        CoroutineScope(IO).
        launch {
            delay(60000)
            CoroutineScope(Main).launch {
                sendNotification(mainFragment.readFireStoreData())
                //mainFragment.readFireStoreData()
                loop()
            }
        }
    }





/**        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(weatherDesc)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //if (mainFragment.remindTime == currentTime.toString()){
        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())*/
            //}

 /*   fun notifyStuff(fireBaseInfo: ArrayList<Reminder>){
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
                i += 1
                val time = it.alertTime;
                val sdf =  SimpleDateFormat("H:mm");
                val dateObj = sdf.parse(time);
                val milliseconds = dateObj.time
                val notifTimer = milliseconds - mNotificationTime
                if(!mNotified){
                    NotificationUtilities().setNotification(mNotificationTime+5000, this@MainActivity)
                }
            }
        }
    }*/

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