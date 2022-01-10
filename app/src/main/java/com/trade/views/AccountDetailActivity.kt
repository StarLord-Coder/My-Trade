package com.trade.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.trade.R
import java.util.*

class AccountDetailActivity : AppCompatActivity() {

    private var btnFilter: Button? = null
    private var tvStartDate: TextView? = null
    private var tvEndDate: TextView? = null
    private var btnReset: Button? = null
    private var btnCancel: TextView? = null
    private var btnConfirm: Button? = null
    private var filterView: RelativeLayout? = null

    private var startDate: String? = ""
    private var endDate: String? = ""

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnFilter = findViewById(R.id.btnFilter)
        tvStartDate = findViewById(R.id.tvStartDate)
        tvEndDate = findViewById(R.id.tvEndDate)
        btnReset = findViewById(R.id.btnReset)
        btnCancel = findViewById(R.id.btnCancel)
        btnConfirm = findViewById(R.id.btnConfirm)
        filterView = findViewById(R.id.filterView)
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

    private fun openStartDatePicker() {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val sDate = "$dayOfMonth/$month/$year"
            tvStartDate?.text = sDate
        }, year, month, day)
        dpd.show()
    }

    private fun openEndDatePicker() {
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val eDate = "$dayOfMonth/$month/$year"
            tvEndDate?.text = eDate
        }, year, month, day)
        dpd.show()
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }

    private fun toAccountTransaction() {
        val intent = Intent(this@AccountDetailActivity, SearchActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}