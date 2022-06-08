package com.thuypham.ptithcm.baselib.base.extension

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment

fun Any.logV(message: String) {
    Log.v(this::class.java.simpleName, message)
}

fun Any.logI(message: String) {
    Log.i(this::class.java.simpleName, message)
}

fun Any.logW(message: String) {
    Log.w(this::class.java.simpleName, message)
}

fun Any.logD(message: String) {
    Log.d(this::class.java.simpleName,  message)
}

fun Activity.logD(message: String) {
    Log.d(this::class.java.simpleName,  message)
}

fun Any.logE(message: String, e: Throwable) {
    Log.e(this::class.java.simpleName, message, e)
}

fun Intent.toDetailedString(): String {
    val builder = StringBuilder("action=$action")
    extras?.let {
        for (key in it.keySet()) {
            builder.append(", $key=${it[key]}")
        }
    }
    return builder.toString()
}