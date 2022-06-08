package com.thuypham.ptithcm.data.model

import com.google.gson.annotations.SerializedName

data class LoginParam(
    var username: String? = "",
    var password: String? = "",

    @SerializedName("request_token")
    var requestToken: String? = ""
)