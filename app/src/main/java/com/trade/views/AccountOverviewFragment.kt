package com.trade.views

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.trade.R
import com.trade.databinding.FragmentAccountOverviewBinding
import com.trade.viewmodel.ProgressIndicatorViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AccountOverviewFragment : Fragment() {

    lateinit var binding: FragmentAccountOverviewBinding
    private val progressIndicatorViewModel: ProgressIndicatorViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_overview, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressIndicatorViewModel.isShowLoadingView.value = false
        initialEvent()
    }

    private fun initialEvent() {

        binding.btnBack?.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
        }

        binding.btnTransaction?.setOnClickListener {
//            progressIndicatorViewModel.isShowLoadingView.value = true
            findNavController().navigate(AccountOverviewFragmentDirections.actionAccountOverviewFragmentToAccountTransactionFragment())
        }
    }


}

