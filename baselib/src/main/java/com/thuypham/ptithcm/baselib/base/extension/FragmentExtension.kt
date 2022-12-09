package com.thuypham.ptithcm.baselib.base.extension

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsets
import android.view.WindowMetrics
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController


fun Fragment.navigateTo(destination: Int, bundle: Bundle? = null, option: NavOptions? = null) {
    try {
        findNavController().navigate(destination, bundle, option)
    } catch (ex: Exception) {
        ex.printErrorLog("navigateTo")
    }
}

fun Fragment.goBack() {
    try {
        findNavController().popBackStack()
    } catch (ex: Exception) {
        ex.printErrorLog("goBack")
    }
}


fun Fragment.navigateToWithAction(action: NavDirections) {
    try {
        findNavController().navigate(action)
    } catch (ex: Exception) {
        ex.printErrorLog("navigateToWithAction")
    }
}


fun Fragment.canGoBack(): Boolean {
    return findNavController().previousBackStackEntry != null
}

fun Fragment.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    requireActivity().getDisplayMetrics()
    return displayMetrics.widthPixels
}

fun Fragment.getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    requireActivity().getDisplayMetrics()
    return displayMetrics.heightPixels
}

fun Activity.getDisplayMetrics(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}

fun Fragment.shareImageFromUrlToOtherApp(imagePath: String) {
    requireActivity().shareImageLocalToOtherApp(imagePath)
}

fun Fragment.shareImageFromUrlToOtherApp(bitmap: Bitmap, fileName: String) {
    requireActivity().shareImageFromUrlToOtherApp(bitmap, fileName)
}


fun Fragment.isNetworkConnected(): Boolean {
    return requireActivity().isNetworkConnected()
}
