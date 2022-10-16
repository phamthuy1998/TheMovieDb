package com.thuypham.ptithcm.data.remote.response
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName


@Parcelize
data class PersonImage(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("profiles")
    var profiles: List<ImageInfo>?
) : Parcelable

