package com.example.mathgame1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //intents
        val intent_game_1 = Intent(this@MainMenuActivity, FirstGameActivity::class.java)
        val btn_game_1 = findViewById(R.id.btn_game_1) as Button
        btn_game_1.setOnClickListener {
            startActivity(intent_game_1)
        }

        val intent_game_2 = Intent(this@MainMenuActivity, SecondGameActivity::class.java)
        val btn_game_2 = findViewById(R.id.btn_game_2) as Button
        btn_game_2.setOnClickListener {
            startActivity(intent_game_2)
        }

        val intent_game_3 = Intent(this@MainMenuActivity, ThirdGameActivity::class.java)
        val btn_game_3 = findViewById(R.id.btn_game_3) as Button
        btn_game_3.setOnClickListener {
            startActivity(intent_game_3)
        }
    }
}