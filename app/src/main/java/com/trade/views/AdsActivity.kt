package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class AdsActivity : AppCompatActivity() {

    lateinit var timer: CountDownTimer
    private var isPauseTimer = false

    private var sec = 3

    private var tvCountdown: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads)
        initialEvent()
        countdownAds(sec)
    }

    private fun initialEvent() {
        tvCountdown = findViewById(R.id.tvCountdown)
        tvCountdown?.setOnClickListener {
            isPauseTimer = true
            toFirstScreen()
            pauseTimer()

//                pauseTimer()
//            if (!isPauseTimer) {
//                isPauseTimer = true
//                pauseTimer()
//            } else {
//                isPauseTimer = false
//                resumeTimer()
//            }
        }
    }

    private fun countdownAds(second: Int) {
        var milliSec = second*1000L
        timer = object: CountDownTimer(milliSec, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvCountdown?.text = "跳过 $sec"
                sec--
            }

            override fun onFinish() {
                toFirstScreen()
            }
        }
        timer.start()
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun resumeTimer() {
        countdownAds(sec)
    }

    private fun toFirstScreen() {
        val intent = Intent(this@AdsActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}