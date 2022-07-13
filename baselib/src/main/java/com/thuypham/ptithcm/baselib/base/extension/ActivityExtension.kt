package com.thuypham.ptithcm.baselib.base.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream


fun AppCompatActivity.shareDataToOtherApp(content: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}


fun Activity.shareImageLocalToOtherApp(imagePath: String) {
    try {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val file = File(imagePath)
            val uri = FileProvider.getUriForFile(this@shareImageLocalToOtherApp, application.packageName, file)
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    } catch (ex: Exception) {
        logE("shareImageToOtherApp error: ${ex.message}", ex)
    }
}


fun Activity.shareImageFromUrlToOtherApp(bitmap: Bitmap, fileName: String) {
    val context = this
    try {
        val sendIntent: Intent = Intent().apply {
//            val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "shared_images", null)

            val cachePath = File(externalCacheDir, "shared_images/")
            cachePath.mkdirs()


            val file = File(cachePath, fileName.removePrefix("/"))
            val fOut = FileOutputStream(file)
            bitmap.compress(CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            val myImageFileUri = FileProvider.getUriForFile(context, applicationContext.packageName, file)

            action = Intent.ACTION_SEND
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_STREAM, myImageFileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/jpeg"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    } catch (ex: Exception) {
        logE("shareImageToOtherApp error: ${ex.message}", ex)
    }
}


@Suppress("DEPRECATION")
fun Activity.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE).convert<ConnectivityManager>()
    return if (Build.VERSION.SDK_INT < 23) {
        cm.activeNetworkInfo
            ?.let { ni ->
                ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            } ?: false
    } else {
        cm.activeNetwork
            ?.let { cm.getNetworkCapabilities(it) }
            ?.let { nc ->
                nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
            ?: false
    }
}

