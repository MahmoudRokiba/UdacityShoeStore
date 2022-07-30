package com.rokiba.udacityshoestore.base

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.rokiba.udacityshoestore.Env
import com.rokiba.udacityshoestore.R
import com.rokiba.udacityshoestore.data.local.MyPreferences
import com.rokiba.udacityshoestore.data.models.base.ApiResponse
import com.rokiba.udacityshoestore.extenstion.logError
import com.rokiba.udacityshoestore.extenstion.openActivity
import com.rokiba.udacityshoestore.extenstion.showToastLong
import com.rokiba.udacityshoestore.extenstion.showToastShort
import com.rokiba.udacityshoestore.ui.splash.SplashActivity
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import org.json.JSONObject
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject


abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val viewModelClass: Class<VM>) :
    LocaleAwareCompatActivity() {

    @Inject
    lateinit var myPreferences: MyPreferences

    @Inject
    lateinit var gson: Gson

    val viewModel by lazy {
        viewModelProvider.get(viewModelClass)
    }

    lateinit var dataBinding: DB

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preInit()
        super.onCreate(savedInstanceState)
        //whiteStatusBarColors()
        dataBinding = DataBindingUtil.setContentView(this, getLayout())
        dataBinding.lifecycleOwner = this
        bindViewModel()
        basicObservers()
        init(savedInstanceState)
    }

    open fun preInit() {

    }

    abstract fun bindViewModel()

    private fun basicObservers() {
        viewModel.isBackPressed.observe(this) {
            if (it) {
                viewModel.isBackPressed.value = false
                finish()
            }
        }

        viewModel.errorResponse.observe(this) {
            handleErrorResponse(it)
        }
    }

    private fun handleErrorResponse(errorResponse: ApiResponse<*>) {
        if (errorResponse.isResponseSuccessful) return

        if (errorResponse.exception != null) {
            if (errorResponse.isCanceled) return

            when (errorResponse.exception) {
                is IOException -> showToastLong(getString(R.string.error_connection))
                is IllegalStateException -> showToastLong(getString(R.string.label_json_error))
                else -> {
                    if (!Env.isProduction) showToastLong(getString(R.string.error_unknown))
                    logError(errorResponse.exception!!.message!!)
                }
            }
        } else if (!errorResponse.isResponseSuccessful) {
            when (errorResponse.responseCode) {
                500 -> showToastShort(getString(R.string.error_server_side))
                400 -> {
                    handleError400(errorResponse)
                }
                401 -> {
                    handleError401(errorResponse)
                }
                else -> {
                    try {
                        val jsonObject = JSONObject(errorResponse.errorBody!!.string())
                        showToastLong(jsonObject.optJSONArray("error")[0].toString())
                    } catch (ignored: Exception) {

                    }
                }
            }
        }
    }

    open fun handleError400(errorResponse: ApiResponse<*>) {
        try {
            val jsonObject = JSONObject(errorResponse.errorBody!!.string())
            showToastLong(jsonObject.optString("msg"))
        } catch (ignored: Exception) {

        }
    }

    open fun handleError401(errorResponse: ApiResponse<*>) {
        try {
            val jsonObject = JSONObject(errorResponse.errorBody!!.string())
            if (jsonObject.optJSONArray("error")[0].toString() == "The access token provided is invalid") {
                //myPreferences.setLogin(null)
                finishAffinity()
                openActivity(SplashActivity::class.java, null, true)
            } else {
                showToastLong(jsonObject.optJSONArray("error")[0].toString())
            }
        } catch (ignored: Exception) {

        }
    }

    abstract fun getLayout(): Int

    abstract fun init(savedInstanceState: Bundle?)

    open fun printHashKey() {
        try {
            val info: PackageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i("BAKAR_TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("BAKAR_TAG", "printHashKey()", e)
        } catch (e: java.lang.Exception) {
            Log.e("BAKAR_TAG", "printHashKey()", e)
        }
    }

    fun byteArrayToHexString(inarray: ByteArray): String? {
        var i: Int
        var j: Int
        var `in`: Int
        val hex = arrayOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F"
        )
        var out = ""
        j = 0
        while (j < inarray.size) {
            `in` = inarray[j].toInt() and 0xff
            i = `in` shr 4 and 0x0f
            out += hex[i]
            i = `in` and 0x0f
            out += hex[i]
            ++j
        }
        return out
    }

}