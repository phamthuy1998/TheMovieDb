package com.thuypham.ptithcm.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageInfo(
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