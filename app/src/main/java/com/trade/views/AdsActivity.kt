package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.trade.R
import kotlinx.android.synthetic.main.activity_ads.*

class AdsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads)
        countdownAds()
    }

    private fun countdownAds() {
        val timer = object: CountDownTimer(3000, 1000) {
            var sec = 3
            override fun onTick(millisUntilFinished: Long) {
                tvCountdown.text = "跳过 $sec"
                sec--
            }

            override fun onFinish() {
                toFirstScreen()
            }
        }
        timer.start()
    }

    private fun toFirstScreen() {
        val intent = Intent(this@AdsActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}