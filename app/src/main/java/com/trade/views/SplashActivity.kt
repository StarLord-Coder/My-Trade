package com.trade.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trade.R
import com.trade.utils.ExchangeModel
import com.trade.utils.SQLiteHelper
import com.trade.utils.getJsonDataFromAsset

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        sqLiteHelper = SQLiteHelper(this)

        Log.v("count", sqLiteHelper.getCount().toString())
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


//        val listData = ReadData().readJsonTx(this)

        Handler().postDelayed({
            val count = sqLiteHelper.getCount()
            if (count == 0L) {
                insertFirstData()
            }
            toAdsScreen()
        }, 3000)
    }

    private fun insertFirstData() {

        val gson = Gson()
        val jsonFileString = getJsonDataFromAsset(applicationContext, "act_c1.json")
        val listTx = object : TypeToken<List<ExchangeModel>>() {}.type
        var txModel: List<ExchangeModel> = gson.fromJson(jsonFileString, listTx)

        for (item in txModel) {

            val tx = ExchangeModel(
                tx_time= item.tx_time,
                record_time= item.record_time,
                tx_day= item.tx_day,
                currency=item.currency ,
                tx_type = item.tx_type,
                tx_amount= item.tx_amount,
                acct_balance= item.acct_balance,
                tx_cat= item.tx_cat,
                channel=item.channel,
                remarks=item.remarks,
                counterparty_acct_name = item.counterparty_acct_name,
                counterparty_acct_no=item.counterparty_acct_no,
                tx_short_date = "", tx_short_time = "")


            val status = sqLiteHelper.insertTXAmount(tx)
        }
        Log.v("count after insert", sqLiteHelper.getCount().toString())
    }

    private fun toAdsScreen() {
        val intent = Intent(this@SplashActivity, AdsActivity::class.java)
        startActivity(intent)
        finish()
    }
}