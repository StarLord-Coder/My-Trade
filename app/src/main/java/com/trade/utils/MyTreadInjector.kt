package com.trade.utils

import com.trade.viewmodel.ProgressIndicatorViewModel
import com.trade.viewmodel.SqlViewModel
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

object MyTreadInjector : KoinComponent {

    fun init() {
        loadKoinModules(
            listOf(
                viewModelModule
            )
        )
    }
}

val viewModelModule = module {
    viewModel { ProgressIndicatorViewModel() }
    viewModel { SqlViewModel() }
}