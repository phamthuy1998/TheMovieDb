package com.thuypham.ptithcm.data.remote.api

import android.util.Log
import com.thuypham.ptithcm.baselib.base.model.AppException
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend inline fun <reified T> wrapApiCall(
    crossinline api: suspend () -> Response<T>
): ResponseHandler<T> {
    return try {
        val response = withContext(Dispatchers.IO) {
            api()
        }
        when (response.code()) {
            // 401 for receive login fail response
            200 -> {
                if (response.body() != null) {
                    ResponseHandler.Success(response.body()!!)
                } else {
                    ResponseHandler.Failure(AppException.NullPoint)
                }
            }
            else -> {
                // Get Error response
//                val gson = Gson()
//                val type = object : TypeToken<ErrorResponse>() {}.type
//                val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()?.charStream(), type)

                ResponseHandler.Failure(AppException.Unknown)
            }
        }
    } catch (exp: Exception) {
        Log.e("wrapApiCall", "Error: ${exp.printStackTrace()}")
        return when (exp) {
            is HttpException -> {
                ResponseHandler.Failure(AppException.NoNetwork)
            }
            is UnknownHostException -> {
                ResponseHandler.Failure(AppException.UnsolvedHost)
            }
            is ConnectException -> {
                ResponseHandler.Failure(AppException.ConnectException)
            }
            is SocketTimeoutException -> {
                ResponseHandler.Failure(AppException.TimeOut)
            }
            else -> {
                ResponseHandler.Failure(exp)
            }
        }
    }
}


fun <T> isEmptyData(data: T): Boolean {
    return data == null
}