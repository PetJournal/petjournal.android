package com.soujunior.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Util {
   fun statusInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}