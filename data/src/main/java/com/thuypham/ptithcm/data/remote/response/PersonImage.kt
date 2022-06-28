package com.thuypham.ptithcm.data.remote.response
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName


@Parcelize
data class PersonImage(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("profiles")
    var profiles: List<Profile>?
) : Parcelable

@Parcelize
data class Profile(
    @SerializedName("aspect_ratio")
    var aspectRatio: Double?,
    @SerializedName("file_path")
    var filePath: String?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    @SerializedName("width")
    var width: Int?
) : Parcelable