package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class MainWithLoginActivity : AppCompatActivity() {

    private var btnMe: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_with_login)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnMe = findViewById(R.id.btnMe)
    }

    private fun initialEvent() {
        btnMe?.setOnClickListener {
            toMe()
        }
    }

    private fun toMe() {
        val intent = Intent(this@MainWithLoginActivity, MeActivity::class.java)
        startActivity(intent)
        finish()
    }
}