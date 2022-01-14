package com.trade.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trade.R
import com.trade.adapter.TransactionAdapter
import com.trade.databinding.FragmentTransactionBinding
import com.trade.utils.EventObserver
import com.trade.utils.ExchangeModel
import com.trade.utils.SQLiteHelper
import com.trade.utils.Transaction
import com.trade.viewmodel.ProgressIndicatorViewModel
import com.trade.viewmodel.SqlViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_main_overview.*
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference


class AccountTransactionFragment : Fragment() {

    lateinit var binding: FragmentTransactionBinding
    private val progressIndicatorViewModel: ProgressIndicatorViewModel by sharedViewModel()
    private val sqlViewModel: SqlViewModel by sharedViewModel()

    private lateinit var sqLiteHelper: SQLiteHelper
    val c = Calendar.getInstance()
    var transactionAdapter: TransactionAdapter? = null
    var transactionList = ArrayList<Transaction>()

    private var startDate: String? = ""
    private var endDate: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sqLiteHelper = SQLiteHelper(requireActivity())
        initialEvent()
//        getTransactionData()
        initialObserver()

//        requireActivity().runOnUiThread {
//
//            getData()
//        }

        progressIndicatorViewModel.isShowLoadingView.value = true

        runBlocking {
            getData()
        }

//        progressIndicatorViewModel.isShowLoadingView.value = false
    }

    override fun onResume() {
        super.onResume()
//        Handler().postDelayed({
//
//            getData()
//        }, 2000)

//        AsyncTask.execute {
//            getData()
//        }

    }

    private fun initialObserver() {
        sqlViewModel.loading.observe(viewLifecycleOwner, EventObserver {
            progressIndicatorViewModel.isShowLoadingView.value = true
        })

        sqlViewModel.success.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            progressIndicatorViewModel.isShowLoadingView.value = false
            initialTransactionData2(it)
        })
    }

    private fun initialEvent() {
        binding.btnFilter?.setOnClickListener {
            binding.filterView?.visibility = View.VISIBLE
        }

        binding.btnCancel?.setOnClickListener {
            binding.filterView?.visibility = View.GONE
        }

        binding.tvStartDate?.setOnClickListener {
            openStartDatePicker()
        }

        binding.tvEndDate?.setOnClickListener {
            openEndDatePicker()
        }

        binding.btnReset?.setOnClickListener {
            resetFilter()
        }

        binding.btnConfirm?.setOnClickListener {
            confirmFilter()
        }

        binding.btnBack?.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private suspend fun getData() {
        if (startDate.isNullOrEmpty() && endDate.isNullOrEmpty()) {
            val tempEnd = getCurrentDateTime()
            val tempStart = getBefore6month()

            startDate = tempStart
            endDate = tempEnd

            sqlViewModel.callSql(requireContext(), startDate, endDate)

            val arrEnd = endDate!!.split(" ")
            binding.tvEndDate?.text = arrEnd[0].replace("-", "/")

            val arrStart = startDate!!.split(" ")
            binding.tvStartDate?.text = arrStart[0].replace("-", "/")
        }
    }

    private fun initialTransactionData() {
        binding.rvTransaction?.layoutManager = LinearLayoutManager(requireActivity())
        transactionAdapter = TransactionAdapter(requireActivity(), transactionList) { position, exchangeModel ->
            toAccountTransaction(exchangeModel)
        }
        binding.rvTransaction?.adapter = transactionAdapter
        progressIndicatorViewModel.isShowLoadingView.value = false
    }

    private fun initialTransactionData2(list: ArrayList<Transaction>) {
        binding.rvTransaction?.layoutManager = LinearLayoutManager(requireActivity())
        transactionAdapter = TransactionAdapter(requireActivity(), list) { position, exchangeModel ->
            toAccountTransaction(exchangeModel)
        }
        binding.rvTransaction?.adapter = transactionAdapter

    }

     fun getTransactionData() {
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
            binding.tvEndDate?.text = arrEnd[0].replace("-", "/")

            val arrStart = startDate!!.split(" ")
            binding.tvStartDate?.text = arrStart[0].replace("-", "/")
        }
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

        DatePickerDialog(requireActivity(),
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

        DatePickerDialog(requireActivity(),
            dateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun resetFilter() {
        binding.tvEndDate?.text = ""
        binding.tvStartDate?.text = ""
    }

    private fun confirmFilter() {
        if (!binding.tvEndDate?.text.toString().isNullOrEmpty() && !binding.tvStartDate?.text.toString().isNullOrEmpty()) {
            endDate = binding.tvEndDate?.text.toString()
            startDate = binding.tvStartDate?.text.toString()

            endDate = endDate!!.replace("/", "-") + " 23:59:59"
            startDate = startDate!!.replace("/", "-") + " 00:00:00"
            binding.filterView?.visibility = View.GONE

            searchData(startDate!!, endDate!!)
            binding.imgBox1?.setImageDrawable(resources.getDrawable(R.drawable.img_account_detail_box_1_red, null))
        } else {
            startDate = ""
            endDate = ""
            getTransactionData()
            binding.imgBox1?.setImageDrawable(resources.getDrawable(R.drawable.img_account_detail_box_1, null))
            binding.filterView?.visibility = View.GONE
        }
    }

    private fun searchData(start: String, end: String) {
        Log.v("start", start)
        Log.v("end", end)
        transactionList = sqLiteHelper.getAllTransactions(start, end)
        initialTransactionData()
    }

    private fun updateStartDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvStartDate!!.text = sdf.format(c.time)
    }

    private fun updateEndDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvEndDate!!.text = sdf.format(c.time)
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Calendar.getInstance().time
        val myFormat = "yyyy-MM-dd 23:59:59"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(currentDate.time)
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

    private fun toAccountTransaction(exchangeModel: ExchangeModel) {

    }

}