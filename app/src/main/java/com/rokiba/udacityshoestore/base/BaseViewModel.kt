package com.rokiba.udacityshoestore.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.rokiba.udacityshoestore.MyApp
import com.rokiba.udacityshoestore.data.models.base.ApiResponse

open class BaseViewModel(open val app: MyApp) : AndroidViewModel(app) {

    /** livData of boolean value used to handle displaying and hiding of progressbar **/
    var isLoading = MutableLiveData<Boolean>().apply { value = false }

    /**
     * MediatorLiveData, all requests will be added as source to it to handle any error or failure.
     **/
    val errorResponse = MediatorLiveData<ApiResponse<*>>()

    val topBarTitle = MutableLiveData<String>()

    var isBackEnabled = MutableLiveData<Boolean>().apply { value = true }

    var isBackPressed = MutableLiveData<Boolean>().apply { value = false }

    var isTopBarButtonEnabled = MutableLiveData<Boolean>().apply { value = false }

    var isTopBarButtonPressed = MutableLiveData<Boolean>().apply { value = false }

    fun backClicked() {
        isBackPressed.value = true
    }

    fun topBarButtonClicked() {
        isTopBarButtonPressed.value = true
    }

}