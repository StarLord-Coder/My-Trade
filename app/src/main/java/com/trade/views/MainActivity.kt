package com.trade.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.trade.R

class MainActivity : AppCompatActivity() {

    private var btnLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setWindowAnimations(0)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//        )

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}