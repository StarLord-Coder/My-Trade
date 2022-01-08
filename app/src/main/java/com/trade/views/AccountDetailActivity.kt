package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class AccountDetailActivity : AppCompatActivity() {

    private var btnFilter: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnFilter = findViewById(R.id.btnFilter)
    }

    private fun initialEvent() {
        btnFilter?.setOnClickListener {
            toAccountTransaction()
        }
    }

    private fun toAccountTransaction() {
        val intent = Intent(this@AccountDetailActivity, SearchActivity::class.java)
        startActivity(intent)
    }
}