package com.rokiba.udacityshoestore.ui.instructions

import android.os.Bundle
import android.view.View
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseFragment
import com.rokiba.udacityshoestore.databinding.FragmentInstructionsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Mahmoud Rokiba
 * Created 7/27/2022 at 10:20 AM
 */
@AndroidEntryPoint
class InstructionsFragment :
    BaseFragment<InstructionsViewModel, FragmentInstructionsBinding>(InstructionsViewModel::class.java) {

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.fragment_instructions

    override fun init(view: View, savedInstanceState: Bundle?) {

        dataBinding.btnNext.setOnClickListener {
            navController.navigate(R.id.action_instructionsFragment_to_listingFragment)
        }

    }

}