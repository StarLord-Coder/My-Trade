package com.trade.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class MainWithLoginActivity : AppCompatActivity() {

    private var btnMe: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_with_login)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}