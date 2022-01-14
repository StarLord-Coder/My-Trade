package com.trade.utils

import android.app.ProgressDialog
import android.content.Context

import android.os.AsyncTask

//class GetDataTask : AsyncTask<Void?, Void?, Void?>() {
//    var dialog: ProgressDialog? = null
//    override fun onPreExecute() {
//        super.onPreExecute()
//    }
//
//
//    override fun onPostExecute(result: Void?) {
//        super.onPostExecute(result)
//        dialog!!.dismiss()
//    }
//
//    override fun doInBackground(vararg p0: Void?): Void? {
//        TODO("Not yet implemented")
//        return null
//    }
//}

class GetDataTask(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}

