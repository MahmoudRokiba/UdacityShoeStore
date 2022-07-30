package com.rokiba.udacityshoestore.data.models.base

import com.google.gson.annotations.SerializedName

data class GeneralResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String?
//    @SerializedName("error")
//    val error: ArrayList<String>?
) : BaseEntity()