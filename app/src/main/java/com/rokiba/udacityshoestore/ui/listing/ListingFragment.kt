package com.rokiba.udacityshoestore.ui.listing

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseFragment
import com.rokiba.udacityshoestore.data.models.response.ShoeItem
import com.rokiba.udacityshoestore.databinding.FragmentListingBinding
import com.rokiba.udacityshoestore.databinding.ItemShoeBinding
import com.rokiba.udacityshoestore.extenstion.showToastShort
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Mahmoud Rokiba
 * Created 7/29/2022 at 2:43 PM
 */
@AndroidEntryPoint
class ListingFragment :
    BaseFragment<ListingViewModel, FragmentListingBinding>(ListingViewModel::class.java) {

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.fragment_listing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.itemList.observe(this) {
            dataBinding.layout.removeAllViews()
            it.forEach { shoeItem ->
                showItem(shoeItem)
            }
            requireActivity().showToastShort(it.size.toString())
        }
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        dataBinding.btnAdd.setOnClickListener {
            navController.navigate(R.id.action_listingFragment_to_detailsScreenFragment)
        }
        setFragmentResultListener("requestKey"){ requestKey, bundle ->
            if (requestKey == "requestKey"){
                val tempList = viewModel.itemList.value!!
                tempList.add(ShoeItem(
                    bundle.getString("name")!!,
                    bundle.getString("description")!!,
                    resources.getDrawable(R.drawable.product_sample),
                    bundle.getFloat("rating", 0.0f)
                ))
                viewModel.itemList.value = tempList
            }
        }
    }

    private fun showItem(item: ShoeItem){
        //val shoeView = layoutInflater.inflate(R.layout.item_shoe, null)
        val itemShoe: ItemShoeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_shoe, null, false)
        itemShoe.item = item
        dataBinding.layout.addView(itemShoe.root)
    }

}