package com.example.mobiledevcw

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Difficulypage:AppCompatActivity() {
    private var playerscore=0
    private var cpuscore=0
    private val buttonClick = AlphaAnimation(1f, 0.8f)
    override fun onCreate(savedInstanceState: Bundle?) {
        playerscore=intent.getIntExtra("player", 0)
        cpuscore=intent.getIntExtra("cpu", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficulty)
        val easy = findViewById<CardView>(R.id.card1)
        val hard = findViewById<CardView>(R.id.card2)
        easy.setOnClickListener(){
            it.startAnimation(buttonClick)
            showNumberPopup("easy")
        }
        hard.setOnClickListener(){
            it.startAnimation(buttonClick)
            showNumberPopup("hard")
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showNumberPopup(tag:String) {//add the difficutly in this param
        val inflater = LayoutInflater.from(this)
        val popupView = inflater.inflate(R.layout.targetscore, null)
        val numberEditText = popupView.findViewById<EditText>(R.id.number_edit_text)
        numberEditText.setText("101")
        AlertDialog.Builder(this)
            .setView(popupView)
            .setPositiveButton("OK") { dialog, which ->
                val inputText = numberEditText.text.toString()
                if (TextUtils.isEmpty(inputText)){
                    numberEditText.setText("101")
                    val number = numberEditText.text.toString().toInt()
                    handleNumber(number,tag)
                }else {
                    val number = numberEditText.text.toString().toInt()
                    handleNumber(number, tag)
                }
            }
            .setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            .show()
    }

    private fun handleNumber(number: Int,tag:String) {
        val intent = Intent(this, NewgameActivity::class.java)
        intent.putExtra("number", number)
        intent.putExtra("player",playerscore)
        intent.putExtra("cpu",cpuscore)
        if (tag=="easy"){
            intent.putExtra("difficulty",1)
        }
        startActivity(intent)
        finish()
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