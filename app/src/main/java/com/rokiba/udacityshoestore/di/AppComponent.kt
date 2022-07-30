package com.rokiba.udacityshoestore.di

import android.content.Context
import com.rokiba.udacityshoestore.MyApp
import com.rokiba.udacityshoestore.base.BaseViewModel
import com.rokiba.udacityshoestore.data.local.MyPreferences
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

    fun app(): MyApp
    fun context(): Context
    fun inject(baseViewModel: BaseViewModel)
    fun getSharedPreference(): MyPreferences

}