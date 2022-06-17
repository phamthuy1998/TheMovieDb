package com.thuypham.ptithcm.data.remote.response

import com.google.gson.annotations.SerializedName

class ListResponse<T>(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: ArrayList<T>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)