package com.example.mobiledevcw

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {

    /**
     * variables to hold last instance of game score
     */
    private var playerscore=0
    private var cpuscore=0
    /**
     * button on-click animantion
     */
    private val buttonClick = AlphaAnimation(1f, 0.8f)

    /**
     * Button variables and popup variable
     */
    private lateinit var dialogact:DialogFragment
    private lateinit var rulesbutton:Button
    private lateinit var newgamebutton:Button
    private lateinit var aboutbutton:Button
    override fun onCreate(savedInstanceState: Bundle?) {

        /**
         * assigning last game win variables through intent
         */

        playerscore=intent.getIntExtra("player", 0)
        cpuscore=intent.getIntExtra("cpu", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *Intializing variables by assigning ID's
         *
         */

        newgamebutton=findViewById(R.id.newgame)
        aboutbutton=findViewById(R.id.button1)
        rulesbutton=findViewById(R.id.button2)

        /**
         * On-click listeners functions
         */
        aboutbutton.setOnClickListener{
            it.startAnimation(buttonClick)
            /**
             * Making an instance of pop-up by passing a layout as a parameter
             */
            dialogact=Popups.newInstance(R.layout.about)
            dialogact.show(supportFragmentManager, "about")

        }
        newgamebutton.setOnClickListener{
            it.startAnimation(buttonClick)
            /**
             * user is directed to the difficulty page where the user can select whether the cpu uses
             * the random strategy or advanced strategy
             */
            val intent = Intent(this, Difficulypage::class.java)
            intent.putExtra("player",playerscore)
            intent.putExtra("cpu",cpuscore)
            startActivity(intent)
        }

        rulesbutton.setOnClickListener {
            it.startAnimation(buttonClick)
            /**
             * Making an instance of pop-up by passing a layout as a parameter
             */
            dialogact=Popups.newInstance(R.layout.rulepage)
            dialogact.show(supportFragmentManager, "rules")
        }
    }

    /**
     * on save and restore overridden functions to save and restore states during orientation change
     */
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