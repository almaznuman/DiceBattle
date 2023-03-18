package com.example.mobiledevcw

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


@Suppress("DEPRECATION")
class NewgameActivity: AppCompatActivity() {
    private val buttonClick = AlphaAnimation(1f, 0.8f)
    private lateinit var throwButton: Button
    private lateinit var cpudice1:ImageView
    private lateinit var cpudice2:ImageView
    private lateinit var cpudice3:ImageView
    private lateinit var cpudice4:ImageView
    private lateinit var cpudice5:ImageView
    private lateinit var dice1: ImageView
    private lateinit var dice2: ImageView
    private lateinit var dice3: ImageView
    private lateinit var dice4: ImageView
    private lateinit var dice5: ImageView
    private lateinit var countTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var scorebutton: Button
    private lateinit var gamewincount: TextView
    private var selectedDice = mutableSetOf<ImageView>()
    private var playertotal=0
    private var cputotal=0
    private var cpuwincount=0
    private var playerwincount=0
    private var cpucurrentroll=0
    private var playercurrentroll=0
    private var targetvalue=0
    private var flagg:Boolean = false
    private var playerroll=0
    private var gamemode=0
    private var cpureroll=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newgame1)
        playerwincount=intent.getIntExtra("player", 0)
        cpuwincount=intent.getIntExtra("cpu", 0)
        gamemode=intent.getIntExtra("difficulty", 0)
        val number = intent.getIntExtra("number", 101)
        targetvalue=number
        throwButton=findViewById(R.id.throwbutton)
        dice1=findViewById(R.id.imageView)
        dice2=findViewById(R.id.imageView1)
        dice3=findViewById(R.id.imageView2)
        dice4=findViewById(R.id.imageView3)
        dice5=findViewById(R.id.imageView4)
        cpudice1=findViewById(R.id.cpuimageView)
        cpudice2=findViewById(R.id.cpuimageView1)
        cpudice3=findViewById(R.id.cpuimageView2)
        cpudice4=findViewById(R.id.cpuimageView3)
        cpudice5=findViewById(R.id.cpuimageView4)
        countTextView=findViewById(R.id.lolz)
        scoreTextView=findViewById(R.id.scoretext)
        scorebutton=findViewById(R.id.scorebutton)
        gamewincount=findViewById(R.id.gamewincount)

        scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
        gamewincount.text="H:$playerwincount / CPU:$cpuwincount"

        throwButton.setOnClickListener {
            disbaledices(true)
            it.startAnimation(buttonClick)
            throwButton.text="Re-Roll"
            flagg=true
            rollplayerdice()
            Dice()
            if (playerroll==3){
                disbaledices(false)
                playertotal+=playercurrentroll
                if (gamemode==1) {
                    randomstrategyreroll()
                }else {
                    advancecpu()
                }
                cputotal+=cpucurrentroll
                scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
                checkwinner()
                playerroll=0
                countTextView.text="Human current Roll: $playercurrentroll"
                playercurrentroll=0
                cpucurrentroll=0
                throwButton.text="Throw"
            }
        }

        scorebutton.setOnClickListener{
            if (flagg) {
                it.startAnimation(buttonClick)
                disbaledices(false)
                throwButton.text="Throw"
                playertotal+=playercurrentroll
                if (gamemode==1) {
                    randomstrategyreroll()
                }else {
                    advancecpu()
                }
                cputotal+=cpucurrentroll
                scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
                checkwinner()
                countTextView.text="Human current Roll: $playercurrentroll"
                playercurrentroll=0
                cpucurrentroll=0
                playerroll=0
                flagg=false
            }
        }
        dice1.setOnClickListener {
            selectDice(dice1)
        }
        dice2.setOnClickListener {
            selectDice(dice2)
        }
        dice3.setOnClickListener {
            selectDice(dice3)
        }
        dice4.setOnClickListener {
            selectDice(dice4)
        }
        dice5.setOnClickListener {
            selectDice(dice5)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("player",playerwincount)
        intent.putExtra("cpu",cpuwincount)
        startActivity(intent)
    }
    private fun total(a:Int,b:Int,c:Int,d:Int,e:Int) {
        cpucurrentroll=a+b+c+d+e

    }
    private fun total(a:Int,b:Int) {
        playercurrentroll=a+b
        countTextView.text = "Human current Roll: $playercurrentroll"

    }

    private fun Dice() {
        if (playerroll<=1) {
            val cpudice1count = Dice(cpudice1)
            val cpudice2count = Dice(cpudice2)
            val cpudice3count = Dice(cpudice3)
            val cpudice4count = Dice(cpudice4)
            val cpudice5count = Dice(cpudice5)
            total(cpudice1count, cpudice2count, cpudice3count, cpudice4count, cpudice5count)
        }
    }

    private fun Dice(dice: ImageView): Int {
        val randomNumber = Random.nextInt(1,7)
        val drawableResource = when (randomNumber) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        val anim = ObjectAnimator.ofFloat(dice, "rotationY", 0f, 360f)
        anim.duration = 500 // Set the duration of the animation in milliseconds

        dice.setTag(randomNumber)
        dice.setImageResource(drawableResource)
        anim.start()
        dice1.alpha = 1.0f
        dice2.alpha = 1.0f
        dice3.alpha = 1.0f
        dice4.alpha = 1.0f
        dice5.alpha = 1.0f
        return randomNumber
    }

    private fun selectDice(dice: ImageView) {
        if (selectedDice.contains(dice)) {
            selectedDice.remove(dice)
            dice.alpha = 1.0f
        } else {
            selectedDice.add(dice)
            dice.alpha = 0.5f
        }
    }

    private fun rollplayerdice() {
        val unselectedDice = setOf(dice1, dice2, dice3, dice4, dice5) - selectedDice
        var newtotal=0
        var retainvalue=0
        for (dice in selectedDice) {
            val backgroundImageName: String = java.lang.String.valueOf(dice.getTag())
            if (backgroundImageName=="1"){
                retainvalue+=1
            }else if ((backgroundImageName=="2")){
                retainvalue+=2
            }else if (backgroundImageName=="3"){
                retainvalue+=3
            }else if ((backgroundImageName=="4")){
                retainvalue+=4
            }else if((backgroundImageName=="5")){
                retainvalue+=5
            }else{
                retainvalue+=6
            }
        }
        for (dice in unselectedDice){
            val dice1Number = Dice(dice)
            newtotal+=dice1Number

        }
        total(retainvalue,newtotal)
        selectedDice.clear()
        dice1.alpha = 1.0f
        dice2.alpha = 1.0f
        dice3.alpha = 1.0f
        dice4.alpha = 1.0f
        dice5.alpha = 1.0f
        playerroll++
    }

    /**
     * Random strategy for cpu
     */

    private fun randomstrategyreroll(){
        val a = (0..2).random()
        val b =(0..1).random()
        val Dices = setOf(cpudice1,cpudice2,cpudice3,cpudice4,cpudice5)
        if (b==0) {
            for (i in 0..a) {
                val a = Random.nextInt(1, 7)
                val randomElements = Dices.asSequence().shuffled().take(a).toList()
                for (dice in randomElements) {
                    val backgroundImageName: String = java.lang.String.valueOf(dice.getTag())
                    cpucurrentroll -= backgroundImageName.toInt()
                    val b = Dice(dice)
                    cpucurrentroll += b
                }
            }
        }
    }

    private fun checkwinner(){
        if(playertotal>=targetvalue && playertotal>cputotal){
            playerwincount++
            val dialogact=Popups.newInstance(R.layout.custompopup)
            dialogact.show(supportFragmentManager, "playerwin")
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
            throwButton.isEnabled=false
            scorebutton.isEnabled=false
            disbaledices(false)
        }else if(cputotal>=targetvalue && cputotal>playertotal){
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
            cpuwincount++
            val dialogact=Popups.newInstance(R.layout.custompopup1)
            dialogact.show(supportFragmentManager, "cpuwin")
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
            throwButton.isEnabled=false
            scorebutton.isEnabled=false
            disbaledices(false)
        }else if(playertotal>=targetvalue && playertotal==cputotal){
            val dialogact=Popups.newInstance(R.layout.custompopup2)
            dialogact.show(supportFragmentManager, "draw")
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
        }
    }
    private fun advancecpu(){
        val Dices = setOf(cpudice1,cpudice2,cpudice3,cpudice4,cpudice5)
        if (cputotal<playertotal) {
            while (cpureroll < 2) {
                for (dice in Dices) {
                    val anim = ObjectAnimator.ofFloat(dice, "rotationY", 0f, 360f)
                    anim.duration = 500 // Set the duration of the animation in milliseconds
                    val backgroundImageName: String = java.lang.String.valueOf(dice.getTag())
                    if (backgroundImageName.toInt() < 3) {
                        cpucurrentroll -= backgroundImageName.toInt()
                        val b = Dice(dice)
                        cpucurrentroll += b
                        anim.start()
                    }
                }
                cpureroll++
            }
            cpureroll=0
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("difficulty", gamemode)
        outState.putInt("playerroll", playerroll)
        outState.putInt("cpureroll", cpureroll)
        outState.putInt("playerwin", playerwincount)
        outState.putInt("cpuwin", cpuwincount)
        outState.putInt("playerscore", playertotal)
        outState.putInt("cpuscore", cputotal)
        outState.putInt("pcurrentroll", playercurrentroll)
        outState.putInt("ccurrentroll", cpucurrentroll)
        outState.putInt("target", targetvalue)
        outState.putInt("dice1", dice1.getTag() as Int)
        outState.putInt("dice2", dice2.getTag() as Int)
        outState.putInt("dice3", dice3.getTag() as Int)
        outState.putInt("dice4", dice4.getTag() as Int)
        outState.putInt("dice5", dice5.getTag() as Int)
        outState.putInt("cpudice1", cpudice1.getTag() as Int)
        outState.putInt("cpudice2", cpudice2.getTag() as Int)
        outState.putInt("cpudice3", cpudice3.getTag() as Int)
        outState.putInt("cpudice4", cpudice4.getTag() as Int)
        outState.putInt("cpudice5", cpudice5.getTag() as Int)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        cpureroll=savedInstanceState.getInt("cpureroll")
        gamemode=savedInstanceState.getInt("difficulty")
        playerroll=savedInstanceState.getInt("playerroll")
        playerwincount = savedInstanceState.getInt("playerwin")
        cpuwincount = savedInstanceState.getInt("cpuwin")
        gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
        playertotal= savedInstanceState.getInt("playerscore")
        cputotal= savedInstanceState.getInt("cpuscore")
        scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
        playercurrentroll= savedInstanceState.getInt("pcurrentroll")
        countTextView.text = "Human current Roll: $playercurrentroll"
        cpucurrentroll= savedInstanceState.getInt("ccurrentroll")
        targetvalue=savedInstanceState.getInt("target")
        restoredice(dice1,savedInstanceState.getInt("dice1"))
        restoredice(dice2,savedInstanceState.getInt("dice2"))
        restoredice(dice3,savedInstanceState.getInt("dice3"))
        restoredice(dice4,savedInstanceState.getInt("dice4"))
        restoredice(dice5,savedInstanceState.getInt("dice5"))
        restoredice(cpudice1,savedInstanceState.getInt("cpudice1"))
        restoredice(cpudice2,savedInstanceState.getInt("cpudice2"))
        restoredice(cpudice3,savedInstanceState.getInt("cpudice3"))
        restoredice(cpudice4,savedInstanceState.getInt("cpudice4"))
        restoredice(cpudice5,savedInstanceState.getInt("cpudice5"))

    }
    private fun restoredice(dice:ImageView,tag:Int){
        val drawableResource = when (tag) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        dice.setTag(tag)
        dice.setImageResource(drawableResource)
    }
    private fun disbaledices(a:Boolean){
        dice1.isEnabled=a
        dice2.isEnabled=a
        dice3.isEnabled=a
        dice4.isEnabled=a
        dice5.isEnabled=a
    }
}