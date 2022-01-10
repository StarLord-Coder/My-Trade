package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class LoginActivity : AppCompatActivity() {

    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun initialEvent() {
        btnLogin?.setOnClickListener {
            toMainWithLogin()
        }
    }

    private fun toMainWithLogin() {
        val intent = Intent(this@LoginActivity, MainWithLoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//        finishAffinity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}