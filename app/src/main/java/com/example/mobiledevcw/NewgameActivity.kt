package com.example.mobiledevcw

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


@Suppress("DEPRECATION")
class NewgameActivity: AppCompatActivity() {
    /**
     * button animations
     */
    private val buttonClick = AlphaAnimation(1f, 0.8f)
    /**
     * Button variables, ImageView varaibles, Textview variables, boolean flags,
     * variables to hold scores, current roll, wins, loses of player and cpu
     */
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
    private var isbuttonenabled=true
    private var isdicesenabled=true
    private var draw=false
    private var dicevisibility=1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newgame1)
        playerwincount=intent.getIntExtra("player", 0)
        cpuwincount=intent.getIntExtra("cpu", 0)
        gamemode=intent.getIntExtra("difficulty", 0)
        val number = intent.getIntExtra("number", 101)
        targetvalue=number

        /**
         *Intializing variables by assigning ID's
         *
         */

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
        countTextView=findViewById(R.id.currentrolltext)
        scoreTextView=findViewById(R.id.scoretext)
        scorebutton=findViewById(R.id.scorebutton)
        gamewincount=findViewById(R.id.gamewincount)
        scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
        gamewincount.text="H:$playerwincount / CPU:$cpuwincount"

        /**
         * enabling onclicklisteners when the activitu begins
         */
        disbaledices(isdicesenabled)
        disablebuttons(isbuttonenabled)
        gameendfunc(dicevisibility)

        throwButton.setOnClickListener(){
            disbaledices(isdicesenabled)
            it.startAnimation(buttonClick)
            throwButton.text="Re-Roll"     // changing the throwbutton text from "throw" to "re-roll"
            flagg=true                     // enabling dices to be individually selected to prevent said dice from being re-rolled

            rollplayerdice()               // rolls player dices
            cpudiceroll() // rolls cpu dices
            if (draw==true){
                disbaledices(false)
                playertotal+=playercurrentroll
                cputotal+=cpucurrentroll
                scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
                playerroll=0
                countTextView.text="Score Updated!"
                checkwinner()
                playercurrentroll=0
                cpucurrentroll=0
                throwButton.text="Throw"
            } else if (playerroll==3){
                disbaledices(false)//disabling dices onclick listeners
                playertotal+=playercurrentroll
                if (gamemode==1) { // obtained from difficulty pagee through intent
                    randomstrategyreroll()// cpu uses random re-roll strategy
                }else {
                    advancecpu() // cpu uses an efficient algorithm for re-rolls
                }
                cputotal+=cpucurrentroll
                scoreTextView.text="Score- H: $playertotal | CPU: $cputotal" //updating scores
                playerroll=0
                countTextView.text="Score Updated!"
                checkwinner()
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
                countTextView.text="Score Updated!"
                checkwinner()
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

    /**
     * overriding onBackPressed to use the native(androids) back button to be directed back to the main menu through intent
     */

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
        countTextView.text = "Human Current Roll: $playercurrentroll"

    }

    private fun cpudiceroll() {
        if (playerroll<=1) {
            val cpudice1count = cpudiceroll(cpudice1)
            val cpudice2count = cpudiceroll(cpudice2)
            val cpudice3count = cpudiceroll(cpudice3)
            val cpudice4count = cpudiceroll(cpudice4)
            val cpudice5count = cpudiceroll(cpudice5)
            total(cpudice1count, cpudice2count, cpudice3count, cpudice4count, cpudice5count)
        }
    }

    private fun cpudiceroll(dice: ImageView): Int {
        val randomNumber = Random.nextInt(1,7)
        val drawableResource = when (randomNumber) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        val anim = ObjectAnimator.ofFloat(dice, "rotationY", 0f, 720f)
        anim.duration = 750

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
            val dice1Number = cpudiceroll(dice)
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
                    val b = cpudiceroll(dice)
                    cpucurrentroll += b
                }
            }
        }
    }

    private fun checkwinner(){
        if(playertotal>=targetvalue && playertotal>cputotal){
            dicevisibility=0.0f
            isbuttonenabled=false
            isdicesenabled=false
            playerwincount++
            val dialogact=Popups.newInstance(R.layout.custompopup)
            dialogact.show(supportFragmentManager, "playerwin")
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
            disablebuttons(isbuttonenabled)
            disbaledices(isdicesenabled)
            gameendfunc(dicevisibility)
        }else if(cputotal>=targetvalue && cputotal>playertotal){
            dicevisibility=0.0f
            isbuttonenabled=false
            isdicesenabled=false
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
            cpuwincount++
            val dialogact=Popups.newInstance(R.layout.custompopup1)
            dialogact.show(supportFragmentManager, "cpuwin")
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
            disablebuttons(isbuttonenabled)
            disbaledices(isdicesenabled)
            gameendfunc(dicevisibility)
        }else if(playertotal>=targetvalue && playertotal==cputotal){
            val dialogact=Popups.newInstance(R.layout.custompopup2)
            dialogact.show(supportFragmentManager, "draw")
            draw=true
            scorebutton.isEnabled=false
            gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
        }
    }
    private fun advancecpu(){
        val Dices = setOf(cpudice1,cpudice2,cpudice3,cpudice4,cpudice5)
        if (cputotal<playertotal) {
            while (cpureroll < 2) {
                for (dice in Dices) {
                    val anim = ObjectAnimator.ofFloat(dice, "rotationY", 0f, 720f)
                    anim.duration = 750
                    val backgroundImageName: String = java.lang.String.valueOf(dice.getTag())
                    if (backgroundImageName.toInt() < 3) {
                        cpucurrentroll -= backgroundImageName.toInt()
                        val b = cpudiceroll(dice)
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
        outState.putString("currentrolltext", countTextView.text.toString())
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
        outState.putString("tag",throwButton.text.toString())
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
        outState.putBoolean("buttonenabled",isbuttonenabled)
        outState.putBoolean("diceenabled",isdicesenabled)
        outState.putFloat("dicevisible",dicevisibility)
        outState.putBoolean("draw",draw)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        draw=savedInstanceState.getBoolean("draw")
        dicevisibility=savedInstanceState.getFloat("dicevisible")
        isbuttonenabled=savedInstanceState.getBoolean("buttonenabled")
        isdicesenabled=savedInstanceState.getBoolean("diceenabled")
        disbaledices(isdicesenabled)
        disablebuttons(isbuttonenabled)
        gameendfunc(dicevisibility)
        cpureroll=savedInstanceState.getInt("cpureroll")
        gamemode=savedInstanceState.getInt("difficulty")
        playerroll=savedInstanceState.getInt("playerroll")
        throwButton.text=savedInstanceState.getString("tag")
        playerwincount = savedInstanceState.getInt("playerwin")
        cpuwincount = savedInstanceState.getInt("cpuwin")
        gamewincount.text="H:$playerwincount / CPU:$cpuwincount"
        playertotal= savedInstanceState.getInt("playerscore")
        cputotal= savedInstanceState.getInt("cpuscore")
        scoreTextView.text="Score- H: $playertotal | CPU: $cputotal"
        playercurrentroll= savedInstanceState.getInt("pcurrentroll")
        countTextView.text = savedInstanceState.getString("currentrolltext")
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
    private fun disablebuttons(a:Boolean){
        throwButton.isEnabled=a
        scorebutton.isEnabled=a
    }
    private fun gameendfunc(a:Float){
        dice1.alpha=a
        dice2.alpha=a
        dice3.alpha=a
        dice4.alpha=a
        dice5.alpha=a
        cpudice1.alpha=a
        cpudice2.alpha=a
        cpudice3.alpha=a
        cpudice4.alpha=a
        cpudice5.alpha=a
        if (a==0.0f) {
            countTextView.text = "The game has ended, head back to the main screen"
        }
    }
}