package com.trade.views

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.trade.R
import com.trade.dialog.LoadingDialog

class AccountOverviewActivity : AppCompatActivity() {

    private var btnTransaction: Button? = null
    private var rlLoading: RelativeLayout? = null
    private var btnBack: Button? = null
    var activity: Context? = null
    var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_overview)
        activity = this@AccountOverviewActivity
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
//            runOnUiThread {
//
//            }
            loadingDialog = LoadingDialog(this)
            loadingDialog!!.setCancelable(false)
            loadingDialog!!.show()
            toAccountTransaction()

        }

        btnBack?.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
        }
    }

     fun changeActivity() {

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