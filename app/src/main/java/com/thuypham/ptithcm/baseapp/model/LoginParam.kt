package com.thuypham.ptithcm.baseapp.model

import com.google.gson.annotations.SerializedName

data class LoginParam(
    var username: String? = "",
    var password: String? = "",

    @SerializedName("request_token")
    var requestToken: String? = ""
)