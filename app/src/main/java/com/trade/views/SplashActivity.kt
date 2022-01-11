package com.trade.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trade.R
import com.trade.utils.ExchangeModel
import com.trade.utils.ReadData
import com.trade.utils.SQLiteHelper
import com.trade.utils.getJsonDataFromAsset

class SplashActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sqLiteHelper = SQLiteHelper(this)

        Log.v("count", sqLiteHelper.getCount().toString())
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val count = sqLiteHelper.getCount()
        if (count == 0L) {
            insertFirstData()
        }
//        val listData = ReadData().readJsonTx(this)

        Handler().postDelayed({
            toFirstScreen()
        }, 3000)
    }

    private fun insertFirstData() {

        val gson = Gson()
        val jsonFileString = getJsonDataFromAsset(applicationContext, "tx.json")
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

    private fun toFirstScreen() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}