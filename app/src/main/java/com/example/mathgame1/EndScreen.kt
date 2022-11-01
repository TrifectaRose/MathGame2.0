package com.example.mathgame1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EndScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        lateinit var scoreTextView: TextView

        val intent = intent
        val YourScore = intent.getIntExtra("YourScore", 0)
        scoreTextView = findViewById(R.id.scoreTextView)
        scoreTextView.text = "Your Score : " + YourScore.toString()
    }
}