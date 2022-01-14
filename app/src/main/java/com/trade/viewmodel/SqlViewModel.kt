package com.trade.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trade.utils.Event
import com.trade.utils.SQLiteHelper
import com.trade.utils.Transaction
import kotlinx.coroutines.launch

class SqlViewModel() : ViewModel() {

    private val _loading = MutableLiveData<Event<Unit>>(Event(Unit))
    val loading: LiveData<Event<Unit>> = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _success = MutableLiveData<ArrayList<Transaction>>()
    val success: LiveData<ArrayList<Transaction>> = _success

    fun callSql(context: Context, startDate: String?, endDate: String?) {
        viewModelScope.launch {
            val sqLiteHelper = SQLiteHelper(context)
//            _loading.postValue(Event(Unit))
            if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {

                val transactionList = sqLiteHelper.getAllTransactions(startDate!!, endDate!!)
                _success.postValue(transactionList)
            } else {
                _error.postValue(Event("Error"))
            }
        }

    }
}