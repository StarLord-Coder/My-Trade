package com.trade.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.trade.R

class LoadingDialog(
    private var mContext: Context
) : AlertDialog(mContext) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.FILL_PARENT
        window?.attributes = params
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}