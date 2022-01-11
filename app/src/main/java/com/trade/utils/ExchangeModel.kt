package com.trade.utils

import java.math.BigDecimal
import java.util.*

data  class ExchangeModel(
//    var id: Int = getnerateAutoId(),
    val tx_short_date: String = "",
    val tx_short_time: String = "",
    var tx_time: String = "",
    var record_time: String = "",
    var tx_day: String = "",
    var currency: String = "",
    var tx_type: String = "",
    var tx_amount: String = "",
    var acct_balance: String = "",
    var tx_cat: String = "",
    var channel: String? = "",
    var remarks: String = "",
    var counterparty_acct_name: String? = "",
    var counterparty_acct_no: String? = ""
) {

    companion object{
        fun generateAutoId():Int{
            val random = Random()
            return random.nextInt(100000000)
        }
    }
}