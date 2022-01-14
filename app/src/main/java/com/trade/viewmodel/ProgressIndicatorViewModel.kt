package com.trade.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgressIndicatorViewModel : ViewModel() {
    val isShowLoadingView = MutableLiveData<Boolean>(false)
}