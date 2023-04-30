package com.trade.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trade.R
import com.trade.adapter.TransactionAdapter
import com.trade.utils.*
import kotlinx.android.synthetic.main.activity_account_detail.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class AccountDetailActivity : AppCompatActivity(){

     lateinit var sqLiteHelper: SQLiteHelper

    private var llFilter: LinearLayout? = null
    private var tvStartDate: TextView? = null
    private var tvEndDate: TextView? = null
    private var btnReset: Button? = null
    private var btnCancel: TextView? = null
    private var btnConfirm: Button? = null
    private var filterView: RelativeLayout? = null
    private var rvTransaction: RecyclerView? = null
    private var rlLoading: RelativeLayout? = null
     var startDate: String? = ""
     var endDate: String? = ""
    private var btnBack: Button? = null
//    private var btnBackFilter: Button? = null
    private var imgBox1: ImageView? = null
    private var icFilter: ImageView? = null
    private var tvTextFilter: TextView? = null
    private var imageBoxGray: ImageView? = null
    val c = Calendar.getInstance()
    var transactionAdapter: TransactionAdapter? = null
    var transactionList = ArrayList<Transaction>()
    private var isShowFilter = false

    private var btn1Day: Button? = null
    private var btn1Month: Button? = null
    private var btn3Month: Button? = null
    private var prevState = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)

//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        sqLiteHelper = SQLiteHelper(this)
        initialComponent()
        initialEvent()
//        val task = MyAsyncTask(this)
//        task.execute(10)

        rlLoading?.visibility = View.VISIBLE
        Handler().postDelayed( {
            getTransactionData()
        }, 2000)
//
//        handler.postDelayed(r, 1000)

//        val handler = Handler(Looper.getMainLooper())
//        handler.post {
//            getTransactionData()
//        }
//        GetDataTask {
//            getTransactionData()
//        }.execute()
    }

    private fun initialComponent() {
        llFilter = findViewById(R.id.llFilter)
        tvStartDate = findViewById(R.id.tvStartDate)
        tvEndDate = findViewById(R.id.tvEndDate)
        btnReset = findViewById(R.id.btnReset)
        btnCancel = findViewById(R.id.btnCancel)
        btnConfirm = findViewById(R.id.btnConfirm)
        filterView = findViewById(R.id.filterView)
        rvTransaction = findViewById(R.id.rvTransaction)
        rlLoading = findViewById(R.id.rlLoading)
        btnBack = findViewById(R.id.btnBack)
//        btnBackFilter = findViewById(R.id.btnBackFilter)
        imgBox1 = findViewById(R.id.imgBox1)
        tvTextFilter = findViewById(R.id.tvTextFilter)
        imageBoxGray = findViewById(R.id.imageBoxGray)
        icFilter = findViewById(R.id.icFilter)
        btn1Day = findViewById(R.id.btn1Day)
        btn1Month = findViewById(R.id.btn1Month)
        btn3Month = findViewById(R.id.btn3Month)
    }

    private fun initialEvent() {
        imageBoxGray?.visibility = View.GONE
        llFilter?.setOnClickListener {
            setRedFilter()
            filterView?.visibility = View.VISIBLE
            isShowFilter = true
            val animSlideIn: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in_right)
            filterView?.startAnimation(animSlideIn)
        }

        btnCancel?.setOnClickListener {
            isShowFilter = false
//            filterView?.visibility = View.GONE
            val animSlideOut: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_right)
            filterView?.startAnimation(animSlideOut)
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

        btn1Day?.setOnClickListener {
            clearState()
            set1Day()
        }

        btn1Month?.setOnClickListener {
            clearState()
            set1Month()
        }

        btn3Month?.setOnClickListener {
            clearState()
            set3Month()
        }
        btnConfirm?.setOnClickListener {
            rlLoading?.visibility = View.VISIBLE
            Handler().postDelayed( {
                confirmFilter()
                rlLoading?.visibility = View.GONE
            }, 500)

        }

//        btnBackFilter?.setOnClickListener {
//            filterView?.visibility = View.GONE
//        }

        btnBack?.setOnClickListener {
            if (isShowFilter) {
                isShowFilter = false

                val animSlideOut: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_right)
                filterView?.startAnimation(animSlideOut)
                filterView?.visibility = View.GONE
            } else {
                finish()
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
            }

        }
    }

    private fun set1Day() {
        prevState = "1_DAY"
        btn1Day?.setTextColor(ContextCompat.getColor(this, R.color.color_red_button_filter))
        btn1Day?.background = getDrawable(R.drawable.bg_btn_red_white)
        tvStartDate?.text = getBefore1week(tvEndDate?.text.toString());
    }

    private fun set1Month() {
        prevState = "1_MONTH"
        btn1Month?.setTextColor(ContextCompat.getColor(this, R.color.color_red_button_filter))
        btn1Month?.background = getDrawable(R.drawable.bg_btn_red_white)
        tvStartDate?.text = getBefore1month(tvEndDate?.text.toString());
    }

    private fun set3Month() {
        prevState = "3_MONTH"
        btn3Month?.setTextColor(ContextCompat.getColor(this, R.color.color_red_button_filter))
        btn3Month?.background = getDrawable(R.drawable.bg_btn_red_white)
        tvStartDate?.text = getBefore3month(tvEndDate?.text.toString());
    }

    private fun clearState() {
        prevState = ""
        btn1Day?.background = getDrawable(R.drawable.bg_btn_gray_white)
        btn1Month?.background = getDrawable(R.drawable.bg_btn_gray_white)
        btn3Month?.background = getDrawable(R.drawable.bg_btn_gray_white)
        btn1Day?.setTextColor(ContextCompat.getColor(this, R.color.color_dark_font_filter))
        btn1Month?.setTextColor(ContextCompat.getColor(this, R.color.color_dark_font_filter))
        btn3Month?.setTextColor(ContextCompat.getColor(this, R.color.color_dark_font_filter))
    }

    private fun setRedFilter() {
        println("red")
        tvTextFilter?.setTextColor(ContextCompat.getColor(this, R.color.color_red_button_filter))
        icFilter?.setImageResource(R.drawable.filter_red_transparent)
    }

    private fun getTransactionData() {
        if (startDate.isNullOrEmpty() && endDate.isNullOrEmpty()) {
            val tempEnd = getCurrentDateTime()
            val tempStart = getBefore6month()

            startDate = tempStart
            endDate = tempEnd
            Log.v("CURRENT END DATE", tempEnd)
            Log.v("CURRENT START DATE", tempStart)
            transactionList = sqLiteHelper.getAllTransactions(tempStart, tempEnd)
            initialTransactionData()
            val arrEnd = endDate!!.split(" ")
            tvEndDate?.text = arrEnd[0].replace("-", "/")

            val arrStart = startDate!!.split(" ")
            tvStartDate?.text = arrStart[0].replace("-", "/")
        }
    }

    private fun initialTransactionData() {
//        val transactionList = ReadData().readJson(this)
        rvTransaction?.layoutManager = LinearLayoutManager(this)
        transactionAdapter = TransactionAdapter(this, transactionList) { position, exchangeModel ->
//            rlLoading?.visibility = View.VISIBLE
            Handler().postDelayed( {
                toAccountTransaction(exchangeModel)
//                rlLoading?.visibility = View.GONE
            }, 500)

        }
        rvTransaction?.adapter = transactionAdapter
        rlLoading?.visibility = View.GONE
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

        if (!tvEndDate?.text.toString().isNullOrEmpty() && !tvStartDate?.text.toString().isNullOrEmpty()) {
            endDate = tvEndDate?.text.toString()
            startDate = tvStartDate?.text.toString()

            endDate = endDate!!.replace("/", "-") + " 23:59:59"
            startDate = startDate!!.replace("/", "-") + " 00:00:00"
            filterView?.visibility = View.GONE

            searchData(startDate!!, endDate!!)
//            imgBox1?.setImageDrawable(resources.getDrawable(R.drawable.img_account_detail_box_1_red, null))
        } else {
            startDate = ""
            endDate = ""
            getTransactionData()
//            imgBox1?.setImageDrawable(resources.getDrawable(R.drawable.img_account_detail_box_1, null))
            filterView?.visibility = View.GONE
        }
        imageBoxGray?.visibility = View.VISIBLE
        isShowFilter = false
    }

    private fun searchData(start: String, end: String) {
        Log.v("start", start)
        Log.v("end", end)
        transactionList = sqLiteHelper.getAllTransactions(start, end)
        initialTransactionData()
    }

//    private fun openDialogFilter() {
//        val filterDialog = FilterDialog(this, "", "")
//        filterDialog.setCancelable(false)
//        filterDialog.show()
//    }

    private fun updateStartDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvStartDate!!.text = sdf.format(c.time)
    }

    private fun updateEndDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvEndDate!!.text = sdf.format(c.time)
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Calendar.getInstance().time
        val myFormat = "yyyy-MM-dd 23:59:59"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(currentDate.time)
    }

    private fun getBefore1week(endDate: String): String {
        val myFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val cal = Calendar.getInstance()
        cal.time = Date(endDate)
        cal.add(Calendar.DATE, -6)
        val d = cal.time
        val res: String = sdf.format(d.time)
        return res
    }

    private fun getBefore1month(endDate: String): String {
        val myFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val cal = Calendar.getInstance()
        cal.time = Date(endDate)
        cal.add(Calendar.MONTH, -1)
        cal.add(Calendar.DATE, +1)
        val d = cal.time
        val res: String = sdf.format(d.time)
        return res
    }

    private fun getBefore3month(endDate: String): String {
        val myFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val cal = Calendar.getInstance()
        cal.time = Date(endDate)
        cal.add(Calendar.MONTH, -3)
        cal.add(Calendar.DATE, +1)
        val d = cal.time
        val res: String = sdf.format(d.time)
        return res
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
        return sdf.format(c.time)
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
        intent.putExtra("tx_type", exchangeModel.tx_type)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    companion object {
        class MyAsyncTask internal constructor(context: AccountDetailActivity) : AsyncTask<Int, String, String?>() {

            private val activityReference: WeakReference<AccountDetailActivity> = WeakReference(context)

            override fun doInBackground(vararg p0: Int?): String? {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return null

                if (activity.startDate.isNullOrEmpty() && activity.endDate.isNullOrEmpty()) {
                    val tempEnd = activity.getCurrentDateTime()
                    val tempStart = activity.getBefore6month()

                    activity.startDate = tempStart
                    activity.endDate = tempEnd
                    Log.v("CURRENT END DATE", tempEnd)
                    Log.v("CURRENT START DATE", tempStart)
                    activity.transactionList = activity.sqLiteHelper.getAllTransactions(tempStart, tempEnd)

                    activity.runOnUiThread {
                        activity.initialTransactionData()
                    }

                    val arrEnd = activity.endDate!!.split(" ")
                    activity.tvEndDate?.text = arrEnd[0].replace("-", "/")

                    val arrStart = activity.startDate!!.split(" ")
                    activity.tvStartDate?.text = arrStart[0].replace("-", "/")

                    activity.rvTransaction?.layoutManager = LinearLayoutManager(activity)
                    activity.transactionAdapter = TransactionAdapter(activity, activity.transactionList) { position, exchangeModel ->
                        activity.toAccountTransaction(exchangeModel)
                    }
                    activity.rvTransaction?.adapter = activity.transactionAdapter
                    activity.rlLoading?.visibility = View.GONE
                }
                return ""
            }

            override fun onPreExecute() {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                activity.rlLoading?.visibility = View.VISIBLE
            }

            override fun onPostExecute(result: String?) {

                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                activity.rlLoading?.visibility = View.GONE
            }

        }
    }
}
