package com.trade.views

import androidx.multidex.MultiDexApplication
import com.trade.utils.MyTreadInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyTradeApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyTradeApplication)
            MyTreadInjector.init()
        }
    }
}