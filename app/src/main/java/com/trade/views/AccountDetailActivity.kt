package com.trade.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trade.R
import com.trade.adapter.TransactionAdapter
import com.trade.utils.*
import java.text.SimpleDateFormat
import java.util.*

class AccountDetailActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper

    private var btnFilter: Button? = null
    private var tvStartDate: TextView? = null
    private var tvEndDate: TextView? = null
    private var btnReset: Button? = null
    private var btnCancel: TextView? = null
    private var btnConfirm: Button? = null
    private var filterView: RelativeLayout? = null
    private var rvTransaction: RecyclerView? = null

    private var startDate: String? = ""
    private var endDate: String? = ""

    val c = Calendar.getInstance()
    var transactionAdapter: TransactionAdapter? = null
    var transactionList = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        sqLiteHelper = SQLiteHelper(this)
        initialComponent()
        initialEvent()
//        initialTransactionData()
        getTransactionData()
    }

    private fun initialComponent() {
        btnFilter = findViewById(R.id.btnFilter)
        tvStartDate = findViewById(R.id.tvStartDate)
        tvEndDate = findViewById(R.id.tvEndDate)
        btnReset = findViewById(R.id.btnReset)
        btnCancel = findViewById(R.id.btnCancel)
        btnConfirm = findViewById(R.id.btnConfirm)
        filterView = findViewById(R.id.filterView)
        rvTransaction = findViewById(R.id.rvTransaction)
    }

    private fun initialEvent() {
        btnFilter?.setOnClickListener {
            filterView?.visibility = View.VISIBLE
        }

        btnCancel?.setOnClickListener {
            filterView?.visibility = View.GONE
        }

        tvStartDate?.setOnClickListener {
            openStartDatePicker()
        }

        tvEndDate?.setOnClickListener {
            openEndDatePicker()
        }

        btnReset?.setOnClickListener {
            resetFilter()
        }

        btnConfirm?.setOnClickListener {
            confirmFilter()
        }
    }

    private fun getTransactionData() {

        if (startDate.isNullOrEmpty() && endDate.isNullOrEmpty()) {
            val tempEnd = getCurrentDateTime()
            val tempStart = getBefore6month()
            Log.v("CURRENT END DATE", tempEnd)
            Log.v("CURRENT START DATE", tempStart)
            transactionList = sqLiteHelper.getAllTransactions(tempStart, tempEnd)
            initialTransactionData()
        }
//        sqLiteHelper.getAllTransactions("", "")
    }

    private fun initialTransactionData() {
//        val transactionList = ReadData().readJson(this)

        rvTransaction?.layoutManager = LinearLayoutManager(this)
        transactionAdapter = TransactionAdapter(this, transactionList) { position, exchangeModel ->
            toAccountTransaction(exchangeModel)
        }
        rvTransaction?.adapter = transactionAdapter
    }

    private fun openStartDatePicker() {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateStartDateInView()
            }
        }

        DatePickerDialog(this@AccountDetailActivity,
            dateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun openEndDatePicker() {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEndDateInView()
            }
        }

        DatePickerDialog(this@AccountDetailActivity,
            dateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun resetFilter() {
        tvEndDate?.text = ""
        tvStartDate?.text = ""
    }

    private fun confirmFilter() {
        endDate = tvEndDate?.text.toString()
        startDate = tvStartDate?.text.toString()
        filterView?.visibility = View.GONE
    }

//    private fun openDialogFilter() {
//        val filterDialog = FilterDialog(this, "", "")
//        filterDialog.setCancelable(false)
//        filterDialog.show()
//    }

    private fun updateStartDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvStartDate!!.text = sdf.format(c.getTime())
    }

    private fun updateEndDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvEndDate!!.text = sdf.format(c.getTime())
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Calendar.getInstance().time
        val myFormat = "yyyy-MM-dd 23:59:59"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(currentDate.getTime())
    }

    private fun getBefore6month(): String {
        val myFormat = "yyyy-MM-dd 00:00:00"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.MONTH, -6)
        cal.add(Calendar.DATE, +1)
        val d = cal.time
        val res: String = sdf.format(d.time)
        return res
    }

    private fun convertToDMY(strDate: String): String {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(c.getTime())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    private fun toAccountTransaction(exchangeModel: ExchangeModel) {
        val intent = Intent(this@AccountDetailActivity, TransactionDetailActivity::class.java)
        intent.putExtra("tx_amount", exchangeModel.tx_amount)
        intent.putExtra("counterparty_acct_name", exchangeModel.counterparty_acct_name)
        intent.putExtra("counterparty_acct_no", exchangeModel.counterparty_acct_no)
        intent.putExtra("tx_time", exchangeModel.tx_time)
        intent.putExtra("record_time", exchangeModel.record_time)
        intent.putExtra("tx_cat", exchangeModel.tx_cat)
        intent.putExtra("currency", exchangeModel.currency)
        intent.putExtra("acct_balance", exchangeModel.acct_balance)
        intent.putExtra("channel", exchangeModel.channel)
        intent.putExtra("remarks", exchangeModel.remarks)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}