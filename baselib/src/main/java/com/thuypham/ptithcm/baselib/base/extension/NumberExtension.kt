package com.thuypham.ptithcm.baselib.base.extension

import org.joda.time.LocalDate
import org.joda.time.Years
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun Long.milliSecondToDateFormat(): String {
    val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

fun Long.getAge(): Int {
    return Years.yearsBetween(LocalDate(this), LocalDate()).years
}

fun String.toMillisecond(): Long? {
    return try {
        val dateString = this
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
        date?.time
    } catch (ex: Exception) {
        logD("convert date error: ${ex.message}")
        null
    }
}

fun Long.toTime(): String {
    val hour = TimeUnit.MILLISECONDS.toHours(this)
    val durationFormat = if (hour > 0) {
        val date = Date(this)
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        dateFormat.format(date)
    } else {
        val date = Date(this)
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        dateFormat.format(date)
    }

    return durationFormat
}

fun Long.toSecond(): Int {
    return TimeUnit.MILLISECONDS.toSeconds(this).toInt()
}

fun Int.toSecond(): Int {
    return TimeUnit.MILLISECONDS.toSeconds(this.toLong()).toInt()
}

fun Float.toSecond(): Int {
    return TimeUnit.MILLISECONDS.toSeconds(this.toLong()).toInt()
}


fun Long.toTimeAsHHmmSSS(): String {
    val hour = TimeUnit.MILLISECONDS.toHours(this)
    val durationFormat = if (hour > 0) {
        val date = Date(this)
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        dateFormat.format(date)
    } else {
        val date = Date(this)
        val dateFormat = SimpleDateFormat("00:mm:ss", Locale.getDefault())
        dateFormat.format(date)
    }

    return durationFormat
}


