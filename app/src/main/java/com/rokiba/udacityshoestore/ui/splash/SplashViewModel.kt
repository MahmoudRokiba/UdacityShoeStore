package com.rokiba.udacityshoestore.ui.splash

import com.rokiba.udacityshoestore.MyApp
import com.rokiba.udacityshoestore.base.BaseViewModel
import com.rokiba.udacityshoestore.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Mahmoud Rokiba
 * Created 7/22/2022 at 6:22 PM
 */
@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: Repository, app: MyApp) :
    BaseViewModel(app) {

    }