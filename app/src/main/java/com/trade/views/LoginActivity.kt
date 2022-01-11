package com.trade.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.trade.R

class LoginActivity : AppCompatActivity() {

    private var btnLogin: Button? = null
    private var edtPhone: EditText? = null
    private var edtPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialComponent()
        initialEvent()
    }

    private fun initialComponent() {
        btnLogin = findViewById(R.id.btnLogin)
        edtPhone = findViewById(R.id.edtPhone)
        edtPassword = findViewById(R.id.edtPassword)
    }

    private fun initialEvent() {
        btnLogin?.setOnClickListener {
            edtPhone?.endBatchEdit()
            edtPassword?.endBatchEdit()
            edtPhone?.clearFocus()
            edtPassword?.clearFocus()
            hideSoftKeyboard()
            toMainWithLogin()
        }
    }

    private fun hideKeyboard(view: View) {
        view?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun toMainWithLogin() {
        val intent = Intent(this@LoginActivity, MainWithLoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//        finishAffinity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}