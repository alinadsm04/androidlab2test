package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kz.alina.chat.ChatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the corresponding layout for this activity
        setContentView(R.layout.activity_main)

        // Find the button by its ID
        val buttonLaunchChat = findViewById<Button>(R.id.buttonLaunchChat)
        buttonLaunchChat.setOnClickListener {
            // Create an intent to launch MainActivity (the chat activity)
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}
