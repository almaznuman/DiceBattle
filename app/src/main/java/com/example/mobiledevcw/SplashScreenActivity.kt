package com.example.mobiledevcw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity: AppCompatActivity() {
    /**
     * Splashscreen activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coverpage)
        val intent= Intent(this,Intropageactivity::class.java)
        /**
         * Timer to delay intent for user-friendliness
         */
        Timer().schedule(3000) {
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}