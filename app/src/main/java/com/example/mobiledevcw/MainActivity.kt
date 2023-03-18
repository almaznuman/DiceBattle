package com.example.mobiledevcw

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment


class MainActivity : AppCompatActivity() {
    private var playerscore=0
    private var cpuscore=0
    private val buttonClick = AlphaAnimation(1f, 0.8f)
    private lateinit var dialogact:DialogFragment
    private lateinit var rulesbutton:Button
    private lateinit var newgamebutton:Button
    private lateinit var aboutbutton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        playerscore=intent.getIntExtra("player", 0)
        cpuscore=intent.getIntExtra("cpu", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newgamebutton=findViewById(R.id.newgame)
        aboutbutton=findViewById(R.id.button1)
        rulesbutton=findViewById(R.id.button2)
        aboutbutton.setOnClickListener{
            it.startAnimation(buttonClick)
            dialogact=Popups.newInstance(R.layout.about)
            dialogact.show(supportFragmentManager, "about")

        }
        newgamebutton.setOnClickListener{
            it.startAnimation(buttonClick)
            val intent = Intent(this, Difficulypage::class.java)
            intent.putExtra("player",playerscore)
            intent.putExtra("cpu",cpuscore)
            startActivity(intent)
        }

        rulesbutton.setOnClickListener {
            it.startAnimation(buttonClick)
            dialogact=Popups.newInstance(R.layout.rulepage)
            dialogact.show(supportFragmentManager, "rules")
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("playerscore", playerscore)
        outState.putInt("cpuscore", cpuscore)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        playerscore=savedInstanceState.getInt("playerscore")
        cpuscore=savedInstanceState.getInt("cpuscore")
    }
}