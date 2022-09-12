package com.thuypham.ptithcm.baselib.base.model

sealed class ResponseHandler<out T> {
    /* Success response with data */
    data class Success<out T>(val data: T) : ResponseHandler<T>()

    /* Failed Response with an exception and message */
    data class Error(val error: Throwable = AppException.Unknown, val message: String = "") : ResponseHandler<Nothing>()

    /** Function had already called before and the previous one is executing */
    object Loading : ResponseHandler<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[ErrorCode = $error -- message = $message]"
            Loading -> "Loading"
            Loading -> "Loading more"
            else -> ""
        }
    }

}