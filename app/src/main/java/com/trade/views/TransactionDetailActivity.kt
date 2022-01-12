package com.trade.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.trade.R
import java.text.DecimalFormat

class TransactionDetailActivity : AppCompatActivity() {

    private var tvAmount: TextView? = null
    private var tvTitle: TextView? = null
    private var tvCounterPattyAccountName: TextView? = null
    private var tvTime: TextView? = null
    private var tvRecordTime: TextView? = null
    private var tvCat: TextView? = null
    private var tvCurrencyAccBalance: TextView? = null
    private var tvChannel: TextView? = null
    private var tvRemark: TextView? = null
    private var btnBack: Button? = null

    private var ll1: LinearLayout? = null
    private var ll2: LinearLayout? = null
    private var ll4: LinearLayout? = null
    private var ll5: LinearLayout? = null
    private var ll6: LinearLayout? = null
    private var ll7: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)
        initialComponent()
        initialEvent()

        if (intent.getStringExtra("counterparty_acct_no").toString() == "" ) {
            ll1?.visibility = View.GONE
        } else {
            val accNo = hideMiddleCharacter(intent.getStringExtra("counterparty_acct_no").toString())
            tvCounterPattyAccountName?.text = intent.getStringExtra("counterparty_acct_name").toString() +" "+ accNo
        }

        val amount = String.format("%.2f", intent.getStringExtra("tx_amount")!!.toDouble())
        tvAmount?.text = currencyFormat(amount.toString())

        tvTime?.text = intent.getStringExtra("tx_time").toString().replace("-", "/")
        tvRecordTime?.text = intent.getStringExtra("record_time").toString().replace("-", "/")

        tvCat?.text = intent.getStringExtra("tx_cat").toString()
        val accBalance = String.format("%.2f", intent.getStringExtra("acct_balance")!!.toDouble())
        tvCurrencyAccBalance?.text = intent.getStringExtra("currency").toString() +" "+ currencyFormat(accBalance)
        tvChannel?.text = intent.getStringExtra("channel").toString()

        if (intent.getStringExtra("remarks").isNullOrEmpty()) {
            ll7?.visibility = View.GONE
        } else {
            tvRemark?.text = intent.getStringExtra("remarks").toString()
        }

        if (intent.getStringExtra("tx_type") != "credit") {
            tvTitle?.text = "收入金额 (人民币元)"
        } else {
            tvTitle?.text = "支出金额 (人民币元)"
        }
    }

    private fun initialComponent() {
        tvAmount = findViewById(R.id.tvAmount)
        tvCounterPattyAccountName = findViewById(R.id.tvCounterPattyAccountName)
        tvTime = findViewById(R.id.tvTime)
        tvRecordTime = findViewById(R.id.tvRecordTime)
        tvCat = findViewById(R.id.tvCat)
        tvCurrencyAccBalance = findViewById(R.id.tvCurrencyAccBalance)
        tvChannel = findViewById(R.id.tvChannel)
        tvRemark = findViewById(R.id.tvRemark)
        tvTitle = findViewById(R.id.tvTitle)
        btnBack = findViewById(R.id.btnBack)

        ll1 = findViewById(R.id.ll1)
        ll2 = findViewById(R.id.ll2)
        ll4 = findViewById(R.id.ll4)
        ll5 = findViewById(R.id.ll5)
        ll6 = findViewById(R.id.ll6)
        ll7 = findViewById(R.id.ll7)
    }

    private fun initialEvent() {
        btnBack?.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    private fun currencyFormat(amount: String): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }

    private fun hideMiddleCharacter(word: String): String? {
        val count = word.length
        Log.v("word", word)
        val c1 = word[0]
        val c2 = word[1]
        val c3 = word[2]
        val c4 = word[3]
        val c19 = word[count-1]
        val c18 = word[count-2]
        val c17 = word[count-3]
        val c16 = word[count-4]
        return "$c1$c2$c3$c4 ****** $c16$c17$c18$c19"
    }
}