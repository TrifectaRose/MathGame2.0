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

    class FirstGameActivity : AppCompatActivity() {

    //lateinit variables
    lateinit var buttons: Array<Button>
    lateinit var resultTextView: TextView
    lateinit var validationTextView: TextView
    lateinit var timerProgressBar: ProgressBar

    //variables
    var firstButtonPressed = false
    var firstValue = 0
    var secondValue = 0
    var currentScore = 0
    var answerFromBefore = 0

    //value
    val maxTimeInMillis = 20000L
    val minTimeInMillis = 0L
    val intervalInMillis = 10L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_game)

        //buttons array
        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4)
        )

        for (button in buttons)
        {
            button.setOnClickListener()
            {
                view : View ->
                checkAnswer(view)
            }
        }

        resultTextView = findViewById(R.id.resultTextView)
        validationTextView = findViewById(R.id.validationTextView)
        timerProgressBar = findViewById(R.id.timerProgressBar)
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
                resultTextView.text = ""

                //move to EndScreen
                val intent = Intent(this@FirstGameActivity, EndScreen::class.java)
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
        firstButtonPressed = false
        firstValue = 0
        secondValue = 0

        var randomGenerator = Random(System.currentTimeMillis())

        //random generator stuff
        var result      = randomGenerator.nextInt(1, 6)
        answerFromBefore = result

        var groupOneValue1 = randomGenerator.nextInt(1, (50/result)-1)
        var groupOneValue2 = (50/result) - groupOneValue1
        var groupOneTrueValue1 = result * groupOneValue1
        var groupOneTrueValue2 = result * groupOneValue2

        resultTextView.text = result.toString()

        var groupTwoValue1 = randomGenerator.nextInt(1, 49)
        var groupTwoValue2 = randomGenerator.nextInt(1, 49)


        var arrayInt = arrayOf(0, 1, 2, 3)
        arrayInt.shuffle(randomGenerator)

        //correct values
        buttons[arrayInt[0]].text = groupOneTrueValue1.toString()
        buttons[arrayInt[1]].text = groupOneTrueValue2.toString()

        //random (incorrect) values
        buttons[arrayInt[2]].text = groupTwoValue1.toString()
        buttons[arrayInt[3]].text = groupTwoValue2.toString()

        for (x in arrayInt) println(x)
    }

    private fun checkAnswer(view : View)
    {
        var buttonPressed = view as Button
        println(buttonPressed.text)

        //results here, second button value here
        if (firstButtonPressed)
        {
            secondValue = buttonPressed.text.toString().toInt()
            var resultCheck = (firstValue + secondValue) % answerFromBefore

            //validate answer
            if (resultCheck == 0)
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

        //check the first button value
        else
        {
            firstValue = buttonPressed.text.toString().toInt()
            firstButtonPressed = true
            buttonPressed.isEnabled = false
        }
    }
}