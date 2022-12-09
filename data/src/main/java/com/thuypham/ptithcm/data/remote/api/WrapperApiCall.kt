package com.thuypham.ptithcm.data.remote.api

import com.thuypham.ptithcm.baselib.base.extension.printErrorLog
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
                    ResponseHandler.Error(AppException.NullPoint)
                }
            }
            else -> {
                // Get Error response
//                val gson = Gson()
//                val type = object : TypeToken<ErrorResponse>() {}.type
//                val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()?.charStream(), type)

                ResponseHandler.Error(AppException.Unknown)
            }
        }
    } catch (exp: Exception) {
        exp.printErrorLog("wrapApiCall")
        return when (exp) {
            is HttpException -> {
                ResponseHandler.Error(AppException.NoNetwork)
            }
            is UnknownHostException -> {
                ResponseHandler.Error(AppException.UnsolvedHost)
            }
            is ConnectException -> {
                ResponseHandler.Error(AppException.ConnectException)
            }
            is SocketTimeoutException -> {
                ResponseHandler.Error(AppException.TimeOut)
            }
            else -> {
                ResponseHandler.Error(exp)
            }
        }
    }
}


fun <T> isEmptyData(data: T): Boolean {
    return data == null
}