package com.trade.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R
import kotlinx.android.synthetic.main.activity_me.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MeActivity : AppCompatActivity() {

    private var btnAccountView: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_me)
        window.setWindowAnimations(0)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnAccountView = findViewById(R.id.btnAccountView)
    }

    private fun initialEvent() {
        btnAccountView?.setOnClickListener {
            toMe()
        }

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        val formatted = current.format(formatter)
//        tvDate.text = "$formatted"
    }

    private fun toMe() {
        val intent = Intent(this@MeActivity, AccountOverviewActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}