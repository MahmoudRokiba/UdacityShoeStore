package com.rokiba.udacityshoestore.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.rokiba.udacityshoestore.Env
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.R.string
import com.rokiba.udacityshoestore.data.local.MyPreferences
import com.rokiba.udacityshoestore.data.models.base.ApiResponse
import com.rokiba.udacityshoestore.extenstion.*
import com.rokiba.udacityshoestore.ui.splash.SplashActivity
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(private val viewModelClass: Class<VM>) :
    Fragment() {

    @Inject
    lateinit var myPreferences: MyPreferences

    @Inject
    lateinit var gson: Gson

    //lateinit var fusedLocationClient: FusedLocationProviderClient

    val navController by lazy {
        Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment_content_main
        )
    }

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    open fun hideBottomNavigation(isHide: Boolean) {

    }

    val viewModel: VM by lazy {
        viewModelProvider.get(viewModelClass)
    }

    private var _dataBinding: DB? = null
    val dataBinding get() = _dataBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _dataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        dataBinding.lifecycleOwner = this
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        bindViewModel()
        basicObservers()
        return dataBinding.root
    }

    abstract fun bindViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().whiteStatusBarColors()
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _dataBinding = null
    }

    private fun basicObservers() {
        viewModel.isBackPressed.observe(viewLifecycleOwner) {
            if (it) {
                navController.navigateUp()
//                viewModel.isBackPressed.value = false
//                requireActivity().finish()
            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) {
            handleErrorResponse(it)
        }
    }

    private fun handleErrorResponse(errorResponse: ApiResponse<*>) {
        if (errorResponse.isResponseSuccessful) return

        if (errorResponse.exception != null) {
            if (errorResponse.isCanceled) return

            when (errorResponse.exception) {
                is IOException -> requireActivity().showToastLong(getString(string.error_connection))
                else -> {
                    handleJsonError(errorResponse)
                }
            }
        } else if (!errorResponse.isResponseSuccessful) {
            when (errorResponse.responseCode) {
                500 -> requireActivity().showToastShort(getString(string.error_server_side))
                400 -> {
                    handleError400(errorResponse)
                }
                401 -> {
                    handleError401(errorResponse)
                }
                else -> {
                    try {
                        val jsonObject = JSONObject(errorResponse.errorBody!!.string())
                        requireActivity().showToastLong(jsonObject.optJSONArray("error")[0].toString())
                    } catch (ignored: Exception) {

                    }
                }
            }
        }
    }

    open fun handleJsonError(errorResponse: ApiResponse<*>) {
        if (!Env.isProduction) {
            requireActivity().showToastLong(getString(string.error_unknown))
            if (errorResponse.exception!!.message!!.startsWith(
                    "Expected BEGIN_OBJECT but was BEGIN_ARRAY",
                    false
                )
            ) {
                requireActivity().showToastLong(getString(string.label_json_error))
            }
        }
        requireActivity().logError(errorResponse.exception!!.message!!)
    }

    open fun handleError400(errorResponse: ApiResponse<*>) {
        try {
            val jsonObject = JSONObject(errorResponse.errorBody!!.string())
            requireActivity().showToastLong(jsonObject.optJSONArray("error")[0].toString())
        } catch (ignored: Exception) {

        }
    }

    open fun handleError401(errorResponse: ApiResponse<*>) {
        try {
            val jsonObject = JSONObject(errorResponse.errorBody!!.string())
            if (jsonObject.optJSONArray("error")[0].toString() == "The access token provided is invalid") {
                //myPreferences.setLogin(null)
                requireActivity().finishAffinity()
                requireActivity().openActivity(SplashActivity::class.java, null, true)
            } else {
                requireActivity().showToastLong(jsonObject.optJSONArray("error")[0].toString())
            }
        } catch (ignored: Exception) {

        }
    }

    abstract fun getLayout(): Int

    abstract fun init(view: View, savedInstanceState: Bundle?)
}