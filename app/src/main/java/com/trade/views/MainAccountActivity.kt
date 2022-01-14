package com.trade.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.trade.R
import com.trade.databinding.ActivityMainOverviewBinding
import com.trade.viewmodel.ProgressIndicatorViewModel
import kotlinx.android.synthetic.main.activity_main_overview.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainAccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainOverviewBinding
    private val progressIndicatorViewModel: ProgressIndicatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_overview)

        progressIndicatorViewModel.isShowLoadingView.observe(this, Observer {
            loadingView?.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
    }
}