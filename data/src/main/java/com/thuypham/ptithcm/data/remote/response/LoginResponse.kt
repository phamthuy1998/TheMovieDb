package com.thuypham.ptithcm.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean? = false,
    @SerializedName("expires_at")
    val expiresAt: String? = "",
    @SerializedName("request_token")
    val requestToken: String? = "",
)