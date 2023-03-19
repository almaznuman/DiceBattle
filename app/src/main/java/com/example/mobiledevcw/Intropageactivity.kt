package com.example.mobiledevcw

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Intropageactivity:AppCompatActivity() {

    /**
     * button animations
     */
    private val buttonClick = AlphaAnimation(1f, 0.8f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introscreen)
        val getstartedbutton:Button=findViewById(R.id.getstartted)
        val i = Intent(this,MainActivity::class.java)
        /**
         *Get started button functions
         */
        getstartedbutton.setOnClickListener(){
            it.startAnimation(buttonClick)
            startActivity(i)
            finish()
        }
    }
}