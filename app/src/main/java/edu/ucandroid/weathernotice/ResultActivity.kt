package edu.ucandroid.weathernotice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (intent.getBooleanExtra("notification", false)) { //Just for confirmation
           // txtTitleView.text = intent.getStringExtra("title")
           // txtMsgView.text = intent.getStringExtra("message")

        }
    }
}