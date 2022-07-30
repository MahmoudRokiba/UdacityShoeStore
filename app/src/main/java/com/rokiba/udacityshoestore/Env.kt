package com.rokiba.udacityshoestore

object Env {

    val isProduction = false

    val PREF_NAME = "udacityshoestore"
    val DOMAIN by lazy {
        if (isProduction) {
            "http://daowb.beetleware.com/"
        } else {
            "http://daowb.beetleware.com/"
        }
    }
    //const val GOOGLE_WEB_CLIENT = "902532031429-hvf97n2kiulrvurm1pufivue58kc79bv.apps.googleusercontent.com"
    //const val BASIC_TOKEN = "Basic cm9raWJhX29hdXRoX2NsaWVudDpyb2tpYmFfb2F1dGhfc2VjcmV0"

    val FILES_URL = DOMAIN + ""
    val API_URL = DOMAIN + "api/"
    const val pageLimit = "15"

    const val DESTINATION = "destination"
    const val DESTINATION_HOME = "home"

}