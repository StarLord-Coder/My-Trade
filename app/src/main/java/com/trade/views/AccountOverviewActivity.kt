package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class AccountOverviewActivity : AppCompatActivity() {

    private var btnTransaction: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_overview)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnTransaction = findViewById(R.id.btnTransaction)
    }

    private fun initialEvent() {
        btnTransaction?.setOnClickListener {
            toAccountTransaction()
        }
    }

    private fun toAccountTransaction() {
        val intent = Intent(this@AccountOverviewActivity, AccountDetailActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}