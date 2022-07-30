package com.rokiba.udacityshoestore.ui.login

import com.rokiba.udacityshoestore.MyApp
import com.rokiba.udacityshoestore.base.BaseViewModel
import com.rokiba.udacityshoestore.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Mahmoud Rokiba
 * Created 7/27/2022 at 8:38 AM
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository, app: MyApp) :
    BaseViewModel(app) {
}