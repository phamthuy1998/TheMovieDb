package com.thuypham.ptithcm.baselib.base.extension

import com.google.gson.Gson

fun Any.toJsonString(): String {
    return Gson().toJson(this).toString()
}

fun Throwable.printErrorLog(tagName: String) {
    logD("$tagName catch an exception")
    logE("$tagName: Throwable", this)
    logE("$tagName: message - ${this.message}", this)
    logE("$tagName: stackTraceToString - ${this.stackTraceToString()}", this)
}
