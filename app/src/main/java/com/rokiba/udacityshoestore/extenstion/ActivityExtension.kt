package com.rokiba.udacityshoestore.extenstion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.utils.SpacesItemDecoration

fun Activity.openActivity(targetActivity: Class<*>?, bundle: Bundle?, finish: Boolean) {
    if (finish) this.finish()
    val intent = Intent(this, targetActivity)
    if (bundle != null) intent.putExtras(bundle)
    this.startActivity(intent)
}

fun Activity.encodeHtml(message: String): String {
    return Html.fromHtml(message).toString()
}

fun Activity.showToastLong(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Activity.showToastShort(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Activity.logError(message: String) = Log.wtf("BAKAR Error", message)

fun Activity.hideView(view: View) {
    view.visibility = View.GONE
}

fun Activity.invisibleView(view: View) {
    view.visibility = View.INVISIBLE
}

fun Activity.showView(view: View) {
    view.visibility = View.VISIBLE
}

fun Activity.hideFirstShowSecond(view1: View, view2: View) {
    view1.visibility = View.GONE
    view2.visibility = View.VISIBLE
}

fun Activity.initVerticalRV(
    recyclerView: RecyclerView,
    spanCount: Int,
    space: Int
): GridLayoutManager {
    val gridLayoutManager = GridLayoutManager(this, spanCount, RecyclerView.VERTICAL, false)
    recyclerView.layoutManager = gridLayoutManager
    recyclerView.addItemDecoration(SpacesItemDecoration(space, spanCount, true))
    recyclerView.isNestedScrollingEnabled = false
    return gridLayoutManager
}

fun Activity.initHorizontalRV(
    recyclerView: RecyclerView,
    spanCount: Int,
    space: Int
): GridLayoutManager {
    val gridLayoutManager = GridLayoutManager(this, spanCount, RecyclerView.HORIZONTAL, false)
    recyclerView.layoutManager = gridLayoutManager
    recyclerView.addItemDecoration(SpacesItemDecoration(space, spanCount, false))
    recyclerView.isNestedScrollingEnabled = false
    return gridLayoutManager
}

fun Activity.fillSpinner(spinner: Spinner, list: ArrayList<*>?) {
    val arrayAdapter: ArrayAdapter<*> =
        ArrayAdapter(this, R.layout.item_spinner, list as MutableList<Any>)
    arrayAdapter.setDropDownViewResource(R.layout.item_spinner_selected)
    spinner.adapter = arrayAdapter
}

fun Activity.whiteStatusBarColors() {
    window.statusBarColor = resources.getColor(R.color.white)
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun Activity.setStatusBarColored(@ColorRes color: Int) {
    val window: Window = this.window
    //val background = getDrawable(R.drawable.horizontal_gradient)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColor(color)
    window.decorView.systemUiVisibility = 0
    //window.navigationBarColor = getColor(android.R.color.transparent)
    //window.setBackgroundDrawable(background)
}
