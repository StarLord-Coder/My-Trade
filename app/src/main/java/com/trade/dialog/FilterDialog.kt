package com.trade.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.trade.R

class FilterDialog(
    private var mContext: Context,
    private val startDate: String?,
    private val cancelDate: String?
) : AlertDialog(mContext) {

    var btnReset: Button? = null
    var btnCancel: TextView? = null
    var btnConfirm: Button? = null
    var edtStartDate: EditText? = null
    var edtEndDate: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_filter)
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window?.attributes = params
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindView()
    }

    private fun bindView() {
        btnReset = findViewById(R.id.btnReset)
        btnCancel = findViewById(R.id.btnCancel)
        btnConfirm = findViewById(R.id.btnConfirm)

        edtStartDate = findViewById(R.id.edtStartDate)
        edtEndDate = findViewById(R.id.edtEndDate)

        btnCancel?.setOnClickListener {
            dismiss()
        }
    }
}