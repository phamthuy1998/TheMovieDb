package com.thuypham.ptithcm.data.remote.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MovieGenge(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
) : Parcelable