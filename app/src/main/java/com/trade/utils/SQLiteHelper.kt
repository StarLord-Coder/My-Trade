package com.trade.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.DatabaseUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 6
        private const val DATABASE_NAME = "exchange.db"
        private const val TBL_EXCHANGE = "tbl_exchange"
        private const val ID = "id"
        private const val TX_TIME = "tx_time"
        private const val RECORD_TIME = "record_time"
        private const val TX_DAY = "tx_day"
        private const val CURRENCY = "currency"
        private const val TX_TYPE = "tx_type"
        private const val TX_AMOUNT = "tx_amount"
        private const val ACCT_BALANCE = "acct_balance"
        private const val TX_CAT = "tx_cat"
        private const val CHANNEL = "channel"
        private const val REMARKS = "remarks"
        private const val COUNTER_PARTY_ACT_NAME = "counterparty_acct_name"
        private const val COUNTER_PARTY_ACT_NO = "counterparty_acct_no"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblExchange = ("CREATE TABLE " + TBL_EXCHANGE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TX_TIME + " DATETIME,"
                + RECORD_TIME + " DATETIME,"
                + TX_DAY + " DATETIME,"
                + CURRENCY + " TEXT,"
                + TX_TYPE + " TEXT,"
                + TX_AMOUNT + " TEXT,"
                + ACCT_BALANCE + " TEXT,"
                + TX_CAT + " TEXT,"
                + CHANNEL + " TEXT,"
                + REMARKS + " TEXT,"
                + COUNTER_PARTY_ACT_NAME + " TEXT,"
                + COUNTER_PARTY_ACT_NO + " TEXT" + ")")

        db?.execSQL(createTblExchange)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVerion: Int, newVersion: Int) {
        db!!.execSQL(" DROP TABLE IF EXISTS $TBL_EXCHANGE")
        onCreate(db)
    }

    fun insertTXAmount(tx: ExchangeModel): Long {

        val db = this.writableDatabase

        val contentValue = ContentValues()
//        contentValue.put(ID, tx.id)
        contentValue.put(TX_TIME, tx.tx_time)
        contentValue.put(RECORD_TIME, tx.record_time)
        contentValue.put(TX_DAY, tx.tx_day)
        contentValue.put(CURRENCY, tx.currency)
        contentValue.put(TX_TYPE, tx.tx_type)
        contentValue.put(TX_AMOUNT, tx.tx_amount)
        contentValue.put(ACCT_BALANCE, tx.acct_balance)
        contentValue.put(TX_CAT, tx.tx_cat)
        contentValue.put(CHANNEL, tx.channel)
        contentValue.put(REMARKS, tx.remarks)
        contentValue.put(COUNTER_PARTY_ACT_NAME, tx.counterparty_acct_name)
        contentValue.put(COUNTER_PARTY_ACT_NO, tx.counterparty_acct_no)

        val success = db.insert(TBL_EXCHANGE, null, contentValue)
        db.close()
        return success

    }

    @SuppressLint("Range")
    fun getAllTransactions(dateTimeForm: String, dateTimeTo: String): ArrayList<Transaction> {

        val txList: ArrayList<ExchangeModel> = ArrayList()
        val SQL_QUERY = "SELECT * FROM $TBL_EXCHANGE WHERE tx_time BETWEEN '$dateTimeForm' AND '$dateTimeTo'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(SQL_QUERY, null)
        } catch (e: Exception) {
            e.printStackTrace();
            db.execSQL(SQL_QUERY)
            return ArrayList()
        }

        var id: Int
        var txTime: String
        var recordTime: String
        var txDay: String
        var txShortTime: String
        var txShortDate: String
        var currency: String
        var txType: String
        var txAmount: String
        var accBalance: String
        var txCat: String
        var channel: String
        var remarks: String
        var counterPartyAcctName: String
        var counterPartyAcctNo: String

        val transactionList = ArrayList<Transaction>()
        var exchangeList = ArrayList<ExchangeModel>()
        var transaction: Transaction? = null
        var tempMont = ""
        if (cursor.moveToFirst()) {

            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                txTime = cursor.getString(cursor.getColumnIndex("tx_time"))
                recordTime = cursor.getString(cursor.getColumnIndex("record_time"))
                txDay = cursor.getString(cursor.getColumnIndex("tx_day"))
                currency = cursor.getString(cursor.getColumnIndex("currency"))
                txType = cursor.getString(cursor.getColumnIndex("tx_type"))
                txAmount = cursor.getString(cursor.getColumnIndex("tx_amount"))
                accBalance = cursor.getString(cursor.getColumnIndex("acct_balance"))
                txCat = cursor.getString(cursor.getColumnIndex("tx_cat"))
                channel = cursor.getString(cursor.getColumnIndex("channel"))
                remarks = cursor.getString(cursor.getColumnIndex("remarks"))
                counterPartyAcctName = cursor.getString(cursor.getColumnIndex("counterparty_acct_name"))
                counterPartyAcctNo = cursor.getString(cursor.getColumnIndex("counterparty_acct_no"))

                val tx = ExchangeModel(
//                    id = id,
                    tx_time = txTime,
                    tx_short_date = txTime.split(" ").get(0),
                    tx_short_time = txTime.split(" ").get(1),
                    record_time = recordTime,
                    tx_day = txDay,
                    currency = currency,
                    tx_type = txType,
                    tx_amount = txAmount,
                    acct_balance = accBalance,
                    tx_cat = txCat,
                    channel = channel,
                    remarks = remarks,
                    counterparty_acct_name = counterPartyAcctName,
                    counterparty_acct_no = counterPartyAcctNo
                )

                if(tempMont == ""){
                    tempMont = convertFormat(txTime)
                }else{
                    if(tempMont == convertFormat(txTime)){
                        exchangeList.add(tx)
                    }else{
                        transaction = Transaction(
                            month = tempMont,
                            tx_in_month = exchangeList
                        )
                        exchangeList = ArrayList<ExchangeModel>()
                        tempMont = convertFormat(txTime)
                        transactionList.add(transaction)
                    }
                }

//                if (tempMont != convertFormat(txTime)) {
//                    tempMont = convertFormat(txTime)
//                    exchangeList.add(tx)
//                } else {
//                    transaction = Transaction(
//                        month = tempMont,
//                        tx_in_month = exchangeList
//                    )
//                    transactionList.add(transaction)
//                    exchangeList = ArrayList<ExchangeModel>()
//                }

//                if (transactionList.isNullOrEmpty()) {
//                    exchangeList.add(tx)
//                } else {
//                    if (tempMont == convertFormat(txTime)) {
//                        exchangeList.add(tx)
//                    } else {
//                        transaction = Transaction(
//                            month = tempMont,
//                            tx_in_month = exchangeList
//                        )
//                        transactionList.add(transaction)
//                    }
//                }
//                txList.add(tx)
//                if (tempMont == convertFormat(txTime)) {
//                    exchangeList.add(tx)
//                } else {
//                    transaction = Transaction(
//                        month = tempMont,
//                        tx_in_month = exchangeList
//                    )
//                    transactionList.add(transaction)
//                }
            } while (cursor.moveToNext())

        }
        return transactionList
    }

    fun getCount(): Long {
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, TBL_EXCHANGE)
        db.close()
        return count
    }

    private fun convertFormat(fullFormatDate: String): String {
        val arrStr1 = fullFormatDate.split(" ")
        val arrStr2 = arrStr1[0].split("-")
        return arrStr2[1] +"月"+ arrStr2[0]
    }
}