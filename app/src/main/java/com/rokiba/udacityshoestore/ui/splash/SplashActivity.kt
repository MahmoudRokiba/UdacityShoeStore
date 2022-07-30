package com.rokiba.udacityshoestore.ui.splash

import android.os.Bundle
import android.os.Handler
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseActivity
import com.rokiba.udacityshoestore.databinding.ActivitySplashBinding
import com.rokiba.udacityshoestore.extenstion.openActivity
import com.rokiba.udacityshoestore.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity :
    BaseActivity<SplashViewModel, ActivitySplashBinding>(SplashViewModel::class.java) {

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.activity_splash

    override fun init(savedInstanceState: Bundle?) {

        Handler().postDelayed({
            openActivity(MainActivity::class.java, null, true)
        }, 3000)
    }
}