package com.rokiba.udacityshoestore.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class MyBaseAdapter<V : BaseViewModel, U : ViewBinding?, T : Any, S : RecyclerView.ViewHolder>(
    open var activity: BaseActivity<*, *>, open var viewModel: V
) : RecyclerView.Adapter<S>() {

    var dataBinding: U? = null

    var itemsList: ArrayList<T> = ArrayList<T>()

    fun setItems(itemsList: ArrayList<T>) {
        this.itemsList = ArrayList<T>()
        this.itemsList = itemsList
        notifyDataSetChanged()
    }

    fun addItems(itemsList: ArrayList<T>) {
        val position = this.itemsList.size
        this.itemsList.addAll(itemsList)
        notifyItemRangeChanged(position, itemsList.size)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): S

    abstract override fun onBindViewHolder(holder: S, position: Int)

    override fun getItemCount(): Int {
        return itemsList.size
    }
}