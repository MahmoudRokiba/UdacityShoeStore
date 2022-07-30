package com.rokiba.udacityshoestore.ui.login

import android.os.Bundle
import android.view.View
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.base.BaseFragment
import com.rokiba.udacityshoestore.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Mahmoud Rokiba
 * Created 7/27/2022 at 8:38 AM
 */
@AndroidEntryPoint
class LoginFragment :
    BaseFragment<LoginViewModel, FragmentLoginBinding>(LoginViewModel::class.java) {

    override fun bindViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun getLayout() = R.layout.fragment_login

    override fun init(view: View, savedInstanceState: Bundle?) {
        dataBinding.btnLogin.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
    }

}