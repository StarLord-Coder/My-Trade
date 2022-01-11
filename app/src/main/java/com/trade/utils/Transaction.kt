package com.trade.utils

data class Transaction(
    val month: String,
    val tx_in_month: ArrayList<ExchangeModel>?
)

data class TransactionItem(
    val tx_short_date: String,
    val tx_short_time: String,
    val tx_time: String,
    val record_time: String,
    val tx_day: String,
    val currency: String,
    val tx_type: String,
    val tx_amount: Double,
    val acct_balance: Double,
    val tx_cat: String,
    val channel: String,
    val remarks: String,
    val counterparty_acct_name: String,
    val counterparty_acct_no: String,
)