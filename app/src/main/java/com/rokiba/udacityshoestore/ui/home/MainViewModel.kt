package com.rokiba.udacityshoestore.ui.home

import com.rokiba.udacityshoestore.MyApp
import com.rokiba.udacityshoestore.base.BaseViewModel
import com.rokiba.udacityshoestore.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Mahmoud Rokiba
 * Created 7/22/2022 at 7:07 PM
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, app: MyApp) :
    BaseViewModel(app) {

}