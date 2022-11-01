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
    }
}