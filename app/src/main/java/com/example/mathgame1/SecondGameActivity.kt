package com.example.mathgame1

import android.content.Intent
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

class SecondGameActivity : AppCompatActivity() {

    //lateinit variables
    lateinit var buttons: Array<Button>
    lateinit var resultTextView: TextView
    lateinit var validationTextView: TextView
    lateinit var timerProgressBar: ProgressBar

    //variables
    var firstButtonPressed = false
    var chosenAnswer = 0
    var secondValue = 0
    var currentScore = 0
    var answerFromBefore = 0

    var btnValue1 = 0
    var btnValue2 = 0
    var btnValue3 = 0

    //value
    val maxTimeInMillis = 20000L
    val minTimeInMillis = 0L
    val intervalInMillis = 10L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_game)
        //buttons array
        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
        )

        for (button in buttons) {
            button.setOnClickListener()
            { view: View ->
                checkAnswer(view)
            }
        }

        resultTextView = findViewById(R.id.resultTextView)
        validationTextView = findViewById(R.id.validationTextView)
        timerProgressBar = findViewById(R.id.timerProgressBar)
        timerProgressBar.max = (maxTimeInMillis / 1000).toInt()
        timerProgressBar.min = (minTimeInMillis / 1000).toInt()

        //do generateQuestion() function
        generateQuestion()

        val timer = object : CountDownTimer(maxTimeInMillis, intervalInMillis) {
            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = (millisUntilFinished / 1000).toInt()
            }

            //when timer's over
            override fun onFinish() {
                for (button in buttons) {
                    button.isEnabled = false
                }
                resultTextView.text = ""

                //move to EndScreen
                val intent = Intent(this@SecondGameActivity, EndScreen::class.java)
                intent.putExtra("YourScore", currentScore)
                startActivity(intent)
            }
        }
        timer.start()
    }

        private fun generateQuestion()
        {
            for (button in buttons)
                button.isEnabled = true

            //refresh value
            firstButtonPressed = false
            chosenAnswer = 0
            secondValue = 0

            var randomGenerator = Random(System.currentTimeMillis())

            //random generator stuff
            var result      = randomGenerator.nextInt(1, 6)
            answerFromBefore = result

            var btn_No1_Value1 = randomGenerator.nextInt(1, 30)
            var btn_No1_Value2 = randomGenerator.nextInt(1, 30)
            btnValue1 = btn_No1_Value1 + btn_No1_Value2

            var btn_No2_Value1 = randomGenerator.nextInt(1, 30)
            var btn_No2_Value2 = randomGenerator.nextInt(1, 30)
            btnValue2 = btn_No2_Value1 + btn_No2_Value2

            var btn_No3_Value1 = randomGenerator.nextInt(1, 50)
            var btn_No3_Value2 = randomGenerator.nextInt(1, 50)
            btnValue3 = btn_No3_Value1 + btn_No3_Value2

            resultTextView.text = result.toString()

            var arrayInt = arrayOf(0, 1, 2)
            arrayInt.shuffle(randomGenerator)

            //button values based on arrayInt
            buttons[arrayInt[0]].text = btn_No1_Value1.toString()
            buttons[arrayInt[1]].text = btn_No2_Value1.toString()
            buttons[arrayInt[2]].text = btn_No3_Value1.toString()

            for (x in arrayInt) println(x)
        }

        private fun checkAnswer(view : View)
        {
            var buttonPressed = view as Button
            var correctAnswer = 0

            println(buttonPressed.text)
            chosenAnswer = buttonPressed.text.toString().toInt()

            if (btnValue1 >= btnValue2 && btnValue1 >= btnValue3)
                correctAnswer = btnValue1
            else if (btnValue2 >= btnValue1 && btnValue2 >= btnValue3)
                correctAnswer = btnValue2
            else correctAnswer = btnValue3

            if (chosenAnswer == correctAnswer)
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
