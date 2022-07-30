package com.rokiba.udacityshoestore.data.models.response

import android.graphics.drawable.Drawable

data class ShoeItem(
    var name: String,
    var description: String,
	var image: Drawable,
	var rating: Float
)
