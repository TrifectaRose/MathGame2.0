package com.example.mathgame1

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlin.random.Random

class ThirdGameActivity : AppCompatActivity() {

    //lateinit variables
    lateinit var buttons: Array<Button>
    lateinit var validationTextView: TextView
    lateinit var timerProgressBar: ProgressBar

    //variables
    var chosenAnswer = 0
    var currentScore = 0
    var isGreen = false

    //value
    val maxTimeInMillis = 20000L
    val minTimeInMillis = 0L
    val intervalInMillis = 10L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_game)

        //buttons array
        buttons = arrayOf(
            findViewById(R.id.btn_3rdGame_1),
            findViewById(R.id.btn_3rdGame_2),
            findViewById(R.id.btn_3rdGame_3),
            findViewById(R.id.btn_3rdGame_4)
        )


        validationTextView = findViewById(R.id.validationTextView3)
        timerProgressBar = findViewById(R.id.timerProgressBar3)
        timerProgressBar.max = (maxTimeInMillis/1000).toInt()
        timerProgressBar.min = (minTimeInMillis/1000).toInt()

        //do generateQuestion() function
        generateQuestion()

        val timer = object : CountDownTimer(maxTimeInMillis, intervalInMillis)
        {
            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = (millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                for (button in buttons) {
                    button.isEnabled = false
                }

                //move to EndScreen
                val intent = Intent(this@ThirdGameActivity, EndScreen::class.java)
                intent.putExtra("YourScore", currentScore)
                startActivity(intent)
            }
        }

        timer.start()
    }
    //generate question on load
    private fun generateQuestion()
    {
        for (button in buttons)
            button.isEnabled = true

        //refresh value
        isGreen = false
        chosenAnswer = 0

        var randomGenerator = Random(System.currentTimeMillis())

        //random generator stuff
        var greenBtnValue = randomGenerator.nextInt(1, 20)
        var yellowBtnValue1 = randomGenerator.nextInt(1, 20)
        var yellowBtnValue2 = randomGenerator.nextInt(1, 20)
        var yellowBtnValue3 = randomGenerator.nextInt(1, 20)

        var arrayInt = arrayOf(0, 1, 2, 3)
        arrayInt.shuffle(randomGenerator)

        //green values
        buttons[arrayInt[0]].text = greenBtnValue.toString()
        buttons[arrayInt[0]].setBackgroundColor(Color.GREEN)
        buttons[arrayInt[0]].setOnClickListener{
            isGreen = true
            chosenAnswer = greenBtnValue
            checkAnswer()
        }

        //yellow button values
        buttons[arrayInt[1]].text = yellowBtnValue1.toString()
        buttons[arrayInt[2]].text = yellowBtnValue2.toString()
        buttons[arrayInt[3]].text = yellowBtnValue3.toString()
        buttons[arrayInt[1]].setBackgroundColor(Color.BLACK)
        buttons[arrayInt[2]].setBackgroundColor(Color.BLACK)
        buttons[arrayInt[3]].setBackgroundColor(Color.BLACK)

        buttons[arrayInt[1]].setOnClickListener{
            chosenAnswer = yellowBtnValue1
            checkAnswer()
        }
        buttons[arrayInt[2]].setOnClickListener{
            chosenAnswer = yellowBtnValue2
            checkAnswer()
        }
        buttons[arrayInt[3]].setOnClickListener{
            chosenAnswer = yellowBtnValue3
            checkAnswer()
        }

        for (x in arrayInt) println(x)
    }

    private fun checkAnswer()
    {

            var resultCheck = chosenAnswer % 2

            //validate answer
            if ((resultCheck == 0) xor (isGreen == true))
            {
                validationTextView.text = ("Correct!")
                currentScore = currentScore + 1
                generateQuestion()
            }
            else {
                validationTextView.text = ("Wrong...")
                generateQuestion()
            }
    }
}