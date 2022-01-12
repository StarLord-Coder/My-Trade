package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class AccountOverviewActivity : AppCompatActivity() {

    private var btnTransaction: Button? = null
    private var rlLoading: RelativeLayout? = null
    private var btnBack: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_overview)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnTransaction = findViewById(R.id.btnTransaction)
        rlLoading = findViewById(R.id.rlLoading)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun initialEvent() {
        btnTransaction?.setOnClickListener {
            toAccountTransaction()
        }

        btnBack?.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
        }
    }

    private fun toAccountTransaction() {

        val intent = Intent(this@AccountOverviewActivity, AccountDetailActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//        rlLoading?.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}