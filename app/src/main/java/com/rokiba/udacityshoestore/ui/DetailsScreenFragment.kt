package com.rokiba.udacityshoestore.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseFragment
import com.rokiba.udacityshoestore.data.models.response.ShoeItem
import com.rokiba.udacityshoestore.databinding.FragmentDetailsScreenBinding
import com.rokiba.udacityshoestore.ui.listing.ListingViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Mahmoud Rokiba
 * Created 7/29/2022 at 7:03 PM
 */
@AndroidEntryPoint
class DetailsScreenFragment :
    BaseFragment<ListingViewModel, FragmentDetailsScreenBinding>(ListingViewModel::class.java) {

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.fragment_details_screen

    override fun init(view: View, savedInstanceState: Bundle?) {
        dataBinding.btnSave.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", viewModel.tempName)
            bundle.putString("description", viewModel.tempDescription)
            bundle.putFloat("rating", viewModel.tempRate)
            setFragmentResult("requestKey", bundle)
            navController.navigateUp()
        }
    }

}