package com.trade.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.IOException

class ReadData {

    private fun getJsonDataFromAsset(context: Context): String? {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("tx.json")
                .bufferedReader()
                .use { it.readText() }

            println(jsonString)
        } catch (ioException: IOException) {
            Log.d("ERROR", ioException.message.toString())
        }

        return jsonString
    }

    fun readJson(context: Context): List<Transaction> {
        val jsonFileString = getJsonDataFromAsset(context)
        Log.i("data", jsonFileString.toString())
        val gson = Gson()
        val listType = object : TypeToken<List<Transaction>>() {}.type
        var data: List<Transaction> = gson.fromJson(jsonFileString, listType)
        for (tradeModel: Transaction in data) {
            println(tradeModel.month)
        }
        return data
    }

    fun readJsonTx(context: Context): List<ExchangeModel> {
        val jsonFileString = getJsonDataFromAsset(context)
        Log.i("data", jsonFileString.toString())
        val gson = Gson()
        val listType = object : TypeToken<List<ExchangeModel>>() {}.type
        var data: List<ExchangeModel> = gson.fromJson(jsonFileString, listType)
        for (exchangeModel: ExchangeModel in data) {
            println(exchangeModel.acct_balance)
        }
        return data
    }
}