package com.rokiba.udacityshoestore.ui.welcome

import android.os.Bundle
import android.view.View
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseFragment
import com.rokiba.udacityshoestore.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Mahmoud Rokiba
 * Created 7/27/2022 at 9:48 AM
 */
@AndroidEntryPoint
class WelcomeFragment :
    BaseFragment<WelcomeViewModel, FragmentWelcomeBinding>(WelcomeViewModel::class.java) {

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.fragment_welcome

    override fun init(view: View, savedInstanceState: Bundle?) {
        dataBinding.btnNext.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_instructionsFragment)
        }
    }

}