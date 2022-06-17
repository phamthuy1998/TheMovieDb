package com.thuypham.ptithcm.baselib.base.extension

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thuypham.ptithcm.baselib.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.ref.WeakReference
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


fun <T> getViewBinding(inflater: LayoutInflater, clazz: Class<T>) =
    clazz.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, inflater)
        .convert<T>()

fun Context.getSharePref(): SharedPreferences {
    return getSharedPreferences(packageName, Context.MODE_PRIVATE)
}

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun View.showKeyboard() {
    post {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(
            this,
            InputMethodManager.SHOW_IMPLICIT
        )
    }
}

fun View.hideKeyboard() {
    post {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.convert() = this as T
fun String.showToast(context: Context, isLong: Boolean = false): String {
    val length = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(context, this, length).show()
    return this
}

fun Context.showToast(msg: String): String {
    Toast.makeText(
        this,
        msg,
        Toast.LENGTH_SHORT
    ).show()
    return msg
}

fun md5(s: String): String {
    try {
        // Create MD5 Hash
        val digest = MessageDigest
            .getInstance("MD5")
        digest.update(s.toByteArray())
        val messageDigest = digest.digest()

        // Create Hex String
        val hexString = StringBuffer()
        for (i in messageDigest.indices) {
            var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
    }
    return ""
}

fun Context.openUrlBrowser(url: String, urlTitle: String) {
    startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)),
            urlTitle
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}

fun View.isHide(boolean: Boolean) {
    if (boolean) {
        visibility = View.INVISIBLE
        isEnabled = false
    } else {
        visibility = View.VISIBLE
        isEnabled = true
    }
}


suspend inline fun <reified T : Any> getDataFromJsonRawResource(context: WeakReference<Context>, rawFileResID: Int): T? {
    return withContext(Dispatchers.IO) {
        var inputStream: InputStream? = null
        try {
            context.get()?.run {
                inputStream = this.resources.openRawResource(rawFileResID)
                val objectAsString = inputStream?.bufferedReader().use {
                    it?.readText()
                }
                logD("objectAsString: $objectAsString")
                Gson().fromJson<T>(objectAsString, object : TypeToken<T>() {}.type)
            }
        } catch (ex: java.lang.Exception) {
            logE("read json file ($rawFileResID) error: ${ex.message}", ex)
            null
        } finally {
            inputStream?.bufferedReader()?.close()
        }
    }
}

fun Context.getPackageInfo(): PackageInfo? {
    return try {
        packageManager.getPackageInfo(packageName, 0)
    } catch (e: Exception) {
        null
    }
}

fun Context.checkAppPermission(list: List<String>): Boolean {
    return list.map { checkCallingOrSelfPermission(it) }
        .all { it == PackageManager.PERMISSION_GRANTED }
}

fun Int.format(numberOfDigit: Int = 2) = String.format("%0${numberOfDigit}d", this)

fun Date.format(format: String = "dd-MM-yyyy"): String {
    return SimpleDateFormat(format, Locale.ROOT).format(this)
}

fun ViewPager.addPageChangedListener(
    listener: (position: Int) -> Unit,
    onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null
) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(
            position: Int, positionOffset: Float, positionOffsetPixels: Int
        ) {
            onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            listener.invoke(position)
        }

    })
}

fun String.toLongNumber(): Long? {
    return try {
        toLong()
    } catch (e: Exception) {
        null
    }
}

fun String.showLog(tag: String = "CUSTOM_LOG") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, this)
    }
}