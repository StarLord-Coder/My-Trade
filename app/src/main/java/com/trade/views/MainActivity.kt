package com.trade.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.trade.R

class MainActivity : AppCompatActivity() {

    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnLogin = findViewById(R.id.btnToLogin)
    }

    private fun initialEvent() {
        btnLogin?.setOnClickListener {
            toLoginScreen()
        }
    }

    private fun toLoginScreen() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
//        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}