package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class MeActivity : AppCompatActivity() {

    private var btnAccountView: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_me)
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
    }

    private fun toMe() {
        val intent = Intent(this@MeActivity, AccountOverviewActivity::class.java)
        startActivity(intent)
    }
}