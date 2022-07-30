package com.rokiba.udacityshoestore.ui.listing

import androidx.lifecycle.MutableLiveData
import com.rokiba.udacityshoestore.MyApp
import com.rokiba.udacityshoestore.base.BaseViewModel
import com.rokiba.udacityshoestore.data.Repository
import com.rokiba.udacityshoestore.data.models.response.ShoeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Mahmoud Rokiba
 * Created 7/29/2022 at 2:43 PM
 */
@HiltViewModel
class ListingViewModel @Inject constructor(private val repository: Repository, app: MyApp) :
    BaseViewModel(app) {

    var tempName = ""
    var tempDescription = ""
    var tempRate = 0.0f

    val itemList = MutableLiveData<ArrayList<ShoeItem>>(ArrayList<ShoeItem>())

}